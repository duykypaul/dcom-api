package com.duykypaul.dcomapi.beans;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PostBean extends BaseBean<PostBean> {
    private String content;
    private String urlImage;
    private int status;
    private int count;
    private UserBean user = new UserBean();
    private Set<CategoryBean> categories;

    public PostBean() {
    }
}
