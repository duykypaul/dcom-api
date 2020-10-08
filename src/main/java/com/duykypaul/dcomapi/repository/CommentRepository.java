package com.duykypaul.dcomapi.repository;

import com.duykypaul.dcomapi.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
