package com.duykypaul.dcomapi.services.impl;

import com.duykypaul.dcomapi.beans.PostBean;
import com.duykypaul.dcomapi.common.Constant;
import com.duykypaul.dcomapi.models.Category;
import com.duykypaul.dcomapi.models.Post;
import com.duykypaul.dcomapi.models.User;
import com.duykypaul.dcomapi.payload.respone.ResponseBean;
import com.duykypaul.dcomapi.repository.CategoryRepository;
import com.duykypaul.dcomapi.repository.PostRepository;
import com.duykypaul.dcomapi.repository.UserRepository;
import com.duykypaul.dcomapi.services.PostService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    Type lstPostBeanType = new TypeToken<List<PostBean>>() {}.getType();

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> findById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(value -> ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), value, "Success")))
            .orElseGet(() -> ResponseEntity.ok(new ResponseBean(HttpStatus.NOT_FOUND.value(), null, "Post id not found!")));
    }

    @Override
    public ResponseEntity<?> findByUserId(Long id) {
        List<Post> posts = postRepository.findAllByUserId(id);
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), posts, "Success"));
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<Post> posts = postRepository.findAll();
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        List<PostBean> postBeans = modelMapper.map(posts, lstPostBeanType);
        return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), postBeans, "Success"));
    }

    @Override
    public ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        List<Post> posts = postRepository.findAll(paging).getContent();
        List<PostBean> postBeans = modelMapper.map(posts, lstPostBeanType);
        return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), postBeans, "Success"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> save(PostBean postBean, Long userId) {
        try {
            if (StringUtils.isEmpty(postBean.getUrlImage())) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(postBean.getFileImage().getOriginalFilename()));
                Path path = Paths.get(Constant.UPLOAD_ROOT, Constant.UPLOAD_POST);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                try {
                    InputStream inputStream = postBean.getFileImage().getInputStream();
                    Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    postBean.setUrlImage(fileName);
                } catch (IOException ioException) {
                    logger.error(ioException.getMessage(), ioException);
                    return ResponseEntity.ok(new ResponseBean(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, "Failure"));
                }
            }

            Post post = modelMapper.map(postBean, Post.class);

            User user = userRepository.getOne(userId);
            post.setUser(user);

            List<Long> categoryIds = Arrays.stream(postBean.getLstCategoryReq().split(Constant.COMMA)).map(Long::parseLong).collect(Collectors.toList());
            Set<Category> categories = categoryRepository.findByIdIn(categoryIds);
            post.setCategories(categories);

            postRepository.save(post);
            return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), postBean, "Success"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.ok(new ResponseBean(HttpStatus.BAD_REQUEST.value(), null, "Failure"));
        }
    }

    @Override
    public ResponseEntity<?> findAllByCategoryId(Integer pageNo, Integer pageSize, String sortBy, Long id) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Optional<Category> category = categoryRepository.findById(id);
        List<Post> posts = new ArrayList<>();
        if (category.isPresent()) {
            posts = postRepository.findPostsByCategoriesContains(paging, category.get());
        }
        List<PostBean> postBeans = modelMapper.map(posts, lstPostBeanType);
        return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), postBeans, "Success"));
    }

    @Override
    public ResponseEntity<?> findAllBySearchKey(Integer pageNo, Integer pageSize, String sortBy, String key) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        List<Post> posts = postRepository.findAllBySearchKey(paging, key);
        List<PostBean> postBeans = modelMapper.map(posts, lstPostBeanType);
        return ResponseEntity.ok(new ResponseBean(HttpStatus.OK.value(), postBeans, "Success"));
    }
}
