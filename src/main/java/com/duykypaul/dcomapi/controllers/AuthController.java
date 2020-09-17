package com.duykypaul.dcomapi.controllers;

import com.duykypaul.dcomapi.beans.LoginBean;
import com.duykypaul.dcomapi.beans.UserBean;
import com.duykypaul.dcomapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginBean loginBean) {
        return userService.authenticateUser(loginBean);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserBean userBean) {
        return userService.registerUser(userBean);
    }
}
