package com.duykypaul.dcomapi.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
public class PostBean extends BaseBean<PostBean> {

    private String content;
    private String urlImage;
    private int status;
    private int count;

    @JsonIgnore
    MultipartFile fileImage;

    private UserBean user = new UserBean();
    private Set<CategoryBean> categories;

    private String lstCategoryReq;

    public PostBean() {
    }
}
