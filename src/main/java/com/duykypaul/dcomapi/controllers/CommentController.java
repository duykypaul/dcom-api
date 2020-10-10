package com.duykypaul.dcomapi.controllers;

import com.duykypaul.dcomapi.payload.request.CommentReq;
import com.duykypaul.dcomapi.payload.respone.ResponseBean;
import com.duykypaul.dcomapi.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentReq commentReq) {
        try {
            return commentService.save(commentReq);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new ResponseBean(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, "Error"));
        }
    }
}
