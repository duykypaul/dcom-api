package com.duykypaul.dcomapi.beans;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MessageBean {
    private String message;

    public MessageBean(String message) {
        this.message = message;
    }
}
