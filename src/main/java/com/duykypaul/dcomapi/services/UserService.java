package com.duykypaul.dcomapi.services;

import com.duykypaul.dcomapi.beans.LoginBean;
import com.duykypaul.dcomapi.beans.UserBean;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> authenticateUser(LoginBean loginBean);

    ResponseEntity<?> registerUser(UserBean userBean);

    ResponseEntity<?> confirmUserAccount(String token);
}
