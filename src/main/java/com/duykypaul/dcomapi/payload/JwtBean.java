package com.duykypaul.dcomapi.payload;

import com.duykypaul.dcomapi.beans.UserBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtBean {
    //    private Long id;
//    private String username;
//    private String email;
//    private List<String> roles;
    UserBean userBean;
    private String token;
    private String type = "Bearer";

    public JwtBean(String token, UserBean userBean) {
        this.token = token;
        this.userBean = userBean;
    }
}
