package com.duykypaul.dcomapi.controllers;

import com.duykypaul.dcomapi.beans.NewsBean;
import com.duykypaul.dcomapi.models.News;
import com.duykypaul.dcomapi.repository.NewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/news")
    public ResponseEntity<?> saveNews(@Valid @RequestBody NewsBean loginBean) {
        newsRepository.save(modelMapper.map(loginBean, News.class));
        return ResponseEntity.ok("success");
    }

}
