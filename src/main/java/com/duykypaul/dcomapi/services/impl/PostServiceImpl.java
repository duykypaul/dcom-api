package com.duykypaul.dcomapi.services.impl;

import com.duykypaul.dcomapi.models.Post;
import com.duykypaul.dcomapi.models.User;
import com.duykypaul.dcomapi.repository.PostRepository;
import com.duykypaul.dcomapi.repository.UserRepository;
import com.duykypaul.dcomapi.services.PostService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public ResponseEntity<?> findByUserId(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
//            List<Post> posts = postRepository.findAllByUserIs(user.get());
            List<Post> posts = postRepository.findAllByUserId(id);
            return ResponseEntity.ok(posts);
        }
        return null;
    }
}
