package com.duykypaul.dcomapi.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(updatable = false)
    @Getter @Setter
    @CreatedBy
    private String createdBy;

    @Column(updatable = false)
    @Getter @Setter
    @CreatedDate
    private Date createdAt;

    @Column
    @Getter @Setter
    @LastModifiedBy
    private String modifiedBy;

    @Column
    @Getter @Setter
    @LastModifiedDate
    private Date modifiedAt;
}

