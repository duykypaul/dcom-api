package com.duykypaul.dcomapi.services;

import org.springframework.http.ResponseEntity;

public interface PostService {
    ResponseEntity<?> findByUserId(Long id);
}
