package com.duykypaul.dcomapi.services.impl;

import com.duykypaul.dcomapi.beans.MessageBean;
import com.duykypaul.dcomapi.beans.RoleBean;
import com.duykypaul.dcomapi.beans.UserBean;
import com.duykypaul.dcomapi.common.Constant;
import com.duykypaul.dcomapi.models.ConfirmationToken;
import com.duykypaul.dcomapi.models.ERole;
import com.duykypaul.dcomapi.models.Role;
import com.duykypaul.dcomapi.models.User;
import com.duykypaul.dcomapi.payload.JwtBean;
import com.duykypaul.dcomapi.payload.LoginBean;
import com.duykypaul.dcomapi.payload.PasswordBean;
import com.duykypaul.dcomapi.repository.ConfirmationTokenRepository;
import com.duykypaul.dcomapi.repository.RoleRepository;
import com.duykypaul.dcomapi.repository.UserRepository;
import com.duykypaul.dcomapi.security.jwt.JwtUtils;
import com.duykypaul.dcomapi.security.services.EmailSenderService;
import com.duykypaul.dcomapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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

    private final PropertyMap<User, UserBean> propertyMapIgnorePassword = new PropertyMap<User, UserBean>() {
        @Override
        protected void configure() {
            skip(destination.getPassword());
        }
    };

    @Override
    public ResponseEntity<?> signIn(LoginBean loginBean) {
        try {
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginBean.getUsername(), loginBean.getPassword());
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
        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("duykypaul@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
            + Constant.DCOM_API_URL + "/auth/confirm-account?token=" + confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);

        return ResponseEntity.ok(new MessageBean(0, "Please check gmail to confirm your account!"));
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
        return ResponseEntity.ok(new MessageBean(0, "User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: User Id is not found"));
        UserBean userBean = modelMapper.map(user, UserBean.class);
        return ResponseEntity.ok(userBean);
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
    public ResponseEntity<?> changePassword(PasswordBean bean) {
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


}
