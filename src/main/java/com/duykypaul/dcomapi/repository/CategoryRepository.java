package com.duykypaul.dcomapi.repository;

import com.duykypaul.dcomapi.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByName(String name);

    Set<Category> findByIdIn(List<Long> ids);
}
