package com.duykypaul.dcomapi.services;

import com.duykypaul.dcomapi.beans.UserBean;
import com.duykypaul.dcomapi.payload.request.LoginBean;
import com.duykypaul.dcomapi.payload.request.PasswordBean;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> signIn(LoginBean loginBean);

    ResponseEntity<?> signUp(UserBean userBean);

    ResponseEntity<?> confirmUserAccount(String token);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> findAll();

    ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy);

    ResponseEntity<?> changePassword(PasswordBean bean);
}
