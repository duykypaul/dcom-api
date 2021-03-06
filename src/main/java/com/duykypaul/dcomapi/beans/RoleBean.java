package com.duykypaul.dcomapi.beans;

import com.duykypaul.dcomapi.models.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleBean {
    private Long id;
    private ERole name;
}
