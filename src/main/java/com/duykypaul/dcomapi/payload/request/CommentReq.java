package com.duykypaul.dcomapi.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentReq {
    private String content;
    private int upVote;
    private int downVote;
    @NotBlank
    private Long userId;
    @NotBlank
    private Long postId;
}
