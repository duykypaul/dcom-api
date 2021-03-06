package com.duykypaul.dcomapi.beans;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class BaseBean<U> {

    private Long id;

    private String createdBy;

    private Date createdAt;

    private String modifiedBy;

    private Date modifiedAt;

    private List<U> listResult = new ArrayList<>();
}
