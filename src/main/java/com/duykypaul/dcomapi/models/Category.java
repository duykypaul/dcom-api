package com.duykypaul.dcomapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    private String name;

    public Category() {
    }
}
