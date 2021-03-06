package com.duykypaul.dcomapi.payload.respone;

import com.duykypaul.dcomapi.beans.UserBean;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtBean {
    private int status;
    private UserBean userBean;
    private String token;
    private String type = "Bearer";

    public JwtBean(int status, String token, UserBean userBean) {
        this.token = token;
        this.userBean = userBean;
        this.status = status;
    }
}
