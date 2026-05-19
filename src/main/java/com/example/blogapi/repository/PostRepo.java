package com.example.blogapi.repository;

import com.example.blogapi.model.Comment;
import com.example.blogapi.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByAuthorId(Long userId);
    Page<Post> findByAuthorId(Pageable pageable, Long authorId);
    List<Post> findAllByOrderByCreatedAtDesc();
}
