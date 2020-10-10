package com.duykypaul.dcomapi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    private String content;
    private String urlImage;
    private int status;
    private int count;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user = new User();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_category",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @JsonManagedReference
    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "post"
    )
    @OrderBy(value = "createdAt DESC")
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }
}
