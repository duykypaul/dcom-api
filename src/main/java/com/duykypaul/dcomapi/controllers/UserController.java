package com.duykypaul.dcomapi.controllers;

import com.duykypaul.dcomapi.payload.PasswordBean;
import com.duykypaul.dcomapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/findAllPaging")
    public ResponseEntity<?> findAllPaging(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return userService.findAll(pageNo, pageSize, sortBy);
    }

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordBean bean) {
        return userService.changePassword(bean);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody PasswordBean bean) {
        return userService.changePassword(bean);
    }
}
