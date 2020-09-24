package com.duykypaul.dcomapi.services;

import com.duykypaul.dcomapi.beans.PostBean;
import org.springframework.http.ResponseEntity;

public interface PostService {

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> findByUserId(Long id);

    ResponseEntity<?> findAll();

    ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy);

    ResponseEntity<?> save(PostBean postBean, Long userId);

    /*ResponseEntity<?> findAllByCategoryId(Integer pageNo, Integer pageSize, String sortBy, Long id);*/
}
