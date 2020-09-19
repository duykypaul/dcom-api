package com.duykypaul.dcomapi.models;

import com.duykypaul.dcomapi.common.Constant;
import com.duykypaul.dcomapi.common.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken extends BaseEntity {

    private String confirmationToken;

    private Date expirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        this.confirmationToken = UUID.randomUUID().toString();
        this.expirationDate = DateUtils.calculateExpirationDate(Constant.Auth.EXPIRATION);
    }
}
