package com.duykypaul.dcomapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends BaseEntity {
    private String content;
    private int upVote;
    private int downVote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user = new User();

    public Comment() {
    }
}
