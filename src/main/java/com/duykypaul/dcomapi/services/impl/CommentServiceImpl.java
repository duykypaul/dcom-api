package com.duykypaul.dcomapi.services.impl;

import com.duykypaul.dcomapi.beans.CommentBean;
import com.duykypaul.dcomapi.models.Comment;
import com.duykypaul.dcomapi.models.Post;
import com.duykypaul.dcomapi.models.User;
import com.duykypaul.dcomapi.payload.request.CommentReq;
import com.duykypaul.dcomapi.payload.respone.ResponseBean;
import com.duykypaul.dcomapi.repository.CommentRepository;
import com.duykypaul.dcomapi.repository.PostRepository;
import com.duykypaul.dcomapi.repository.UserRepository;
import com.duykypaul.dcomapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> save(CommentBean commentBean, Long userId, Long postId) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<?> save(CommentReq commentReq) {
        try {
            Comment comment = new Comment();
            comment.setContent(commentReq.getContent());
            Post post = postRepository.getOne(commentReq.getPostId());
            User user = userRepository.getOne(commentReq.getUserId());
            comment.setPost(post);
            comment.setUser(user);
            commentRepository.save(comment);
            CommentBean commentBean = modelMapper.map(comment, CommentBean.class);
            return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), commentBean, "Success"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("error!");
        }
    }
}
