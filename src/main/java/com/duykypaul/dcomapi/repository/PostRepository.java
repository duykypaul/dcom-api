package com.duykypaul.dcomapi.repository;

import com.duykypaul.dcomapi.models.Category;
import com.duykypaul.dcomapi.models.Post;
import com.duykypaul.dcomapi.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserIs(User user);

    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findAllByUserId(@Param("userId") Long id);

    /*@Query("select p from Post p where p.categories c")
    Slice<Object> findAllByCategoryId(Pageable paging, Long id);*/

    List<Post> findPostsByCategoriesContains(Pageable paging, Category category);

    @Query("select p from Post p where p.user.username like %:key% or p.content like %:key%")
    List<Post> findAllBySearchKey(Pageable paging, String key);

}
