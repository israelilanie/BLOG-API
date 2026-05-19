package com.example.blogapi.repository;

import com.example.blogapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByAuthorId(Long userId);
}
