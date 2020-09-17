package com.duykypaul.dcomapi.services.impl;

import com.duykypaul.dcomapi.beans.JwtBean;
import com.duykypaul.dcomapi.beans.LoginBean;
import com.duykypaul.dcomapi.beans.MessageBean;
import com.duykypaul.dcomapi.beans.UserBean;
import com.duykypaul.dcomapi.models.ERole;
import com.duykypaul.dcomapi.models.Role;
import com.duykypaul.dcomapi.models.User;
import com.duykypaul.dcomapi.repository.RoleRepository;
import com.duykypaul.dcomapi.repository.UserRepository;
import com.duykypaul.dcomapi.security.jwt.JwtUtils;
import com.duykypaul.dcomapi.security.services.UserDetailsImpl;
import com.duykypaul.dcomapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

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

    @Override
    public ResponseEntity<?> authenticateUser(LoginBean loginBean) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginBean.getUsername(), loginBean.getPassword());
        Authentication authentication = authenticationManager.authenticate(authReq);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtBean(jwtToken, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @Override
    public ResponseEntity<?> registerUser(UserBean userBean) {
        if (userRepository.existsByUsername(userBean.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageBean("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(userBean.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageBean("Error: Email is already taken!"));
        }

        userBean.setPassword(passwordEncoder.encode(userBean.getPassword()));
        User user = modelMapper.map(userBean, User.class);

        Set<String> roleNames = userBean.getRoles();
        Set<Role> roles = new HashSet<>();

        if (null == roleNames) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            roleNames.forEach(role -> {
                switch (role.toLowerCase()) {
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

        return ResponseEntity.ok(new MessageBean("User registered successfully!"));
    }
}
