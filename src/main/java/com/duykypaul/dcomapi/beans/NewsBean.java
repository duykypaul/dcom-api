package com.duykypaul.dcomapi.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NewsBean extends BaseBean<NewsBean> {
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
}
