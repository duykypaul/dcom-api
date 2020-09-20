package com.duykypaul.dcomapi.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter @Setter
public class UserBean extends BaseBean<UserBean> {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(min = 6, max = 30)
    private String password;

    private Set<RoleBean> roles;

    private String firstName;
    private String lastName;
    private String description;
    private String gender;
    private String yourViewed;
    private String youViewed;
    private String lastLogin;
    private String profilePicture;
    private String profileViews;
    private String permission;
    private boolean isEnabled;
}
