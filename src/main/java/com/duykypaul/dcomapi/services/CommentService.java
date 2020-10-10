package com.duykypaul.dcomapi.services;

import com.duykypaul.dcomapi.beans.CommentBean;
import com.duykypaul.dcomapi.payload.request.CommentReq;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> save(CommentBean commentBean, Long userId, Long postId);

    ResponseEntity<?> save(CommentReq commentReq);
}
