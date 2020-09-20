package com.duykypaul.dcomapi.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "category", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }
}
