package com.duykypaul.dcomapi.controllers;

import com.duykypaul.dcomapi.beans.PostBean;
import com.duykypaul.dcomapi.common.Constant;
import com.duykypaul.dcomapi.security.jwt.JwtUtils;
import com.duykypaul.dcomapi.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService postService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/{id}/user")
    public ResponseEntity<?> findByUserId(@PathVariable Long id) {
        return postService.findByUserId(id);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return postService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> findAllPaging(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "createdAt") String sortBy) {
        return postService.findAll(pageNo, pageSize, sortBy);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOnePost(HttpServletRequest request, @ModelAttribute PostBean postBean) {
        String token = jwtUtils.parseJwt(request);
        Long userId = Long.valueOf(jwtUtils.findIdByJwtToken(token));
        return postService.save(postBean, userId);
    }

    /*@GetMapping("/pagination/{id}/category")
    public ResponseEntity<?> findAllPaging(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "id") String sortBy, @PathVariable Long id) {
        return PostService.findAllByCategoryId(pageNo, pageSize, sortBy, id);
    }*/

    @GetMapping("/get-image/{image}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable String image) {
        if (!StringUtils.isEmpty(image)) {
            try {
                Path filename = Paths.get(Constant.UPLOAD_ROOT, Constant.UPLOAD_POST, image);
                byte[] buffer = Files.readAllBytes(filename);
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return ResponseEntity.ok()
                    .contentLength(buffer.length)
                    .contentType(MediaType.parseMediaType("image/png"))
                    .body(byteArrayResource);
            } catch (IOException io) {
                logger.error(io.getMessage(), io);
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
