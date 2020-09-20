package com.duykypaul.dcomapi.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswordBean {
    @NotBlank
    @Size(min = 3, max = 30)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 30)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 30)
    private String reNewPassword;
}
