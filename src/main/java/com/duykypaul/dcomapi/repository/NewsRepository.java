package com.duykypaul.dcomapi.repository;

import com.duykypaul.dcomapi.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

}
