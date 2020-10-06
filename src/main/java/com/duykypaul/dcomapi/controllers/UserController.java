package com.duykypaul.dcomapi.controllers;

import com.duykypaul.dcomapi.beans.UserBean;
import com.duykypaul.dcomapi.common.Constant;
import com.duykypaul.dcomapi.payload.request.PasswordBean;
import com.duykypaul.dcomapi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> findAllPaging(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return userService.findAll(pageNo, pageSize, sortBy);
    }

    @PostMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @Valid @RequestBody PasswordBean bean) {
        return userService.changePassword(bean);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @ModelAttribute UserBean userBean) {
        return userService.save(id, userBean);
    }

    @GetMapping("/get-image/{image}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable String image) {
        if (!StringUtils.isEmpty(image)) {
            try {
                Path filename = Paths.get(Constant.UPLOAD_ROOT, Constant.UPLOAD_USER, image);
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
