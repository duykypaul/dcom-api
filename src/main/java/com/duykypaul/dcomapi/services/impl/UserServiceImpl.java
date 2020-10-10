package com.duykypaul.dcomapi.services.impl;

import com.duykypaul.dcomapi.beans.RoleBean;
import com.duykypaul.dcomapi.beans.UserBean;
import com.duykypaul.dcomapi.common.Constant;
import com.duykypaul.dcomapi.models.ConfirmationToken;
import com.duykypaul.dcomapi.models.ERole;
import com.duykypaul.dcomapi.models.Role;
import com.duykypaul.dcomapi.models.User;
import com.duykypaul.dcomapi.payload.request.LoginReq;
import com.duykypaul.dcomapi.payload.request.PasswordReq;
import com.duykypaul.dcomapi.payload.respone.JwtBean;
import com.duykypaul.dcomapi.payload.respone.MessageBean;
import com.duykypaul.dcomapi.payload.respone.ResponseBean;
import com.duykypaul.dcomapi.repository.ConfirmationTokenRepository;
import com.duykypaul.dcomapi.repository.RoleRepository;
import com.duykypaul.dcomapi.repository.UserRepository;
import com.duykypaul.dcomapi.security.jwt.JwtUtils;
import com.duykypaul.dcomapi.security.services.EmailSenderService;
import com.duykypaul.dcomapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ResponseEntity<?> signIn(LoginReq loginReq) {
        try {
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword());
            Authentication authentication = authenticationManager.authenticate(authReq);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = jwtUtils.generateJwtToken(authentication);

            Optional<User> user = userRepository.findByUsername(authentication.getName());
            UserBean userBean = new UserBean();
            if (user.isPresent()) {
                userBean = modelMapper.map(user.get(), UserBean.class);
            }

            return ResponseEntity.ok(new JwtBean(HttpStatus.OK.value(), jwtToken, userBean));
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new MessageBean(HttpStatus.UNAUTHORIZED.value(), "Email or password invalid!"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> signUp(UserBean userBean) {
        ModelMapper modelMapper = new ModelMapper();
        if (userRepository.existsByUsername(userBean.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageBean(0, "Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(userBean.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageBean(0, "Email is already taken!"));
        }

        userBean.setPassword(passwordEncoder.encode(userBean.getPassword()));
        User user = modelMapper.map(userBean, User.class);

        Set<RoleBean> roleBeans = userBean.getRoles();
        Set<Role> roles = new HashSet<>();

        if (null == roleBeans) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            roleBeans.forEach(role -> {
                switch (role.getName().name().toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        user.setProfilePicture(Constant.Auth.AVATAR_DEFAULT);
        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("duykypaul@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
            + Constant.DCOM_VUE_URL + "/" + confirmationToken.getConfirmationToken());
        emailSenderService.sendEmail(mailMessage);

        return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), userBean, "Please check gmail to confirm your account!"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> confirmUserAccount(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken)
            .orElseThrow(() -> new RuntimeException("Error: The token was not found"));

        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("Error: Email is not found"));

            if (user.isEnabled()) {
                return ResponseEntity.badRequest().body(new MessageBean(0, "Error: Your account has been previously verified!"));
            }

            if (token.getExpirationDate().getTime() < new Date(System.currentTimeMillis()).getTime()) {
                return ResponseEntity.badRequest().body(new MessageBean(0, "Error: The confirmation Time has expired!"));
            }

            user.setEnabled(true);
            userRepository.save(user);
        }
        return ResponseEntity.ok(new MessageBean(HttpStatus.OK.value(), "User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User Id is not found"));
            UserBean userBean = modelMapper.map(user, UserBean.class);
            return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), userBean, "Success"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new ResponseBean(HttpStatus.UNAUTHORIZED.value(), null, "UnAuthorized"));
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<User> users = userRepository.findAll();
        return convertListUserToUserBean(users);
    }

    @Override
    public ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        List<User> users = userRepository.findAll(paging).getContent();
        return convertListUserToUserBean(users);
    }

    @Override
    @Transactional
    public ResponseEntity<?> changePassword(PasswordReq bean) {
        return ResponseEntity.ok("ok");
    }

    private ResponseEntity<?> convertListUserToUserBean(List<User> users) {
        List<UserBean> userBeans = new ArrayList<>();
        users.forEach((user) -> {
            UserBean userBean = modelMapper.map(user, UserBean.class);
            userBeans.add(userBean);
        });
        return ResponseEntity.ok(userBeans);
    }

    @Override
    @Transactional
    public ResponseEntity<?> save(Long id, UserBean userBean) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User is not found"));
            user.setUsername(userBean.getUsername());
            user.setGender(userBean.getGender());
            user.setDescription(userBean.getDescription());
            user.setUsername(userBean.getUsername());

            if (userBean.getFileImage() != null && !userBean.getFileImage().isEmpty()) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(userBean.getFileImage().getOriginalFilename()));
                Path path = Paths.get(Constant.UPLOAD_ROOT, Constant.UPLOAD_USER);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                try {
                    InputStream inputStream = userBean.getFileImage().getInputStream();
                    Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    user.setProfilePicture(fileName);
                } catch (IOException ioException) {
                    logger.error(ioException.getMessage(), ioException);
                    return ResponseEntity.ok(new ResponseBean(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, "Failure"));
                }
            }

            userRepository.save(user);
            return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), user, "Success"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new MessageBean(HttpStatus.BAD_REQUEST.value(), "Cannot update profile!"));
        }
    }
}
