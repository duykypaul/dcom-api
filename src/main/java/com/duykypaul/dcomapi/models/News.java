package com.duykypaul.dcomapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "news")
public class News extends BaseEntity {

    @Column(length = 20)
    private String name;

    public News() {
    }
}
