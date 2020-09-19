package com.duykypaul.dcomapi.config;

import com.duykypaul.dcomapi.common.Constant;
import com.duykypaul.dcomapi.models.ERole;
import com.duykypaul.dcomapi.models.Role;
import com.duykypaul.dcomapi.models.User;
import com.duykypaul.dcomapi.repository.RoleRepository;
import com.duykypaul.dcomapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!roleRepository.findByName(ERole.ROLE_ADMIN).isPresent()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }

        if (!roleRepository.findByName(ERole.ROLE_MODERATOR).isPresent()) {
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        }

        if (!roleRepository.findByName(ERole.ROLE_USER).isPresent()) {
            roleRepository.save(new Role(ERole.ROLE_USER));
        }

        // Admin account
        if (!userRepository.findByEmail(Constant.Auth.ADMIN_EMAIL).isPresent()) {
            User admin = new User();
            admin.setEmail(Constant.Auth.ADMIN_EMAIL);
            admin.setPassword(passwordEncoder.encode(Constant.Auth.ADMIN_PASSWORD));
            admin.setUsername(Constant.Auth.ADMIN_NAME);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
            roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR).get());
            roles.add(roleRepository.findByName(ERole.ROLE_USER).get());
            admin.setRoles(roles);
            admin.setEnabled(true);
            userRepository.save(admin);
        }
    }
}
