package com.duykypaul.dcomapi.controllers;

import com.duykypaul.dcomapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService PostService;

    @GetMapping
    public ResponseEntity<?> findByUserId(@RequestParam(defaultValue = "1") Long userId) {
        return PostService.findByUserId(userId);
    }
}
