package com.duykypaul.dcomapi.services;

import com.duykypaul.dcomapi.beans.UserBean;
import com.duykypaul.dcomapi.payload.request.LoginReq;
import com.duykypaul.dcomapi.payload.request.PasswordReq;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> signIn(LoginReq loginReq);

    ResponseEntity<?> signUp(UserBean userBean);

    ResponseEntity<?> confirmUserAccount(String token);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> findAll();

    ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy);

    ResponseEntity<?> changePassword(PasswordReq bean);

    ResponseEntity<?> save(Long id, UserBean userBean);
}
