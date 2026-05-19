package com.example.blogapi.repository;

import com.example.blogapi.model.Comment;
import com.example.blogapi.model.CommentLike;
import com.example.blogapi.model.Post;
import com.example.blogapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);

    boolean existsByUserAndComment(User user, Comment comment);

    void deleteByUserAndComment(User user, Comment comment);

    long countByCommentId(long commentId);
}
