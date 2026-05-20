package com.example.blogapi.service;

import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.model.*;
import com.example.blogapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final UserRepo userRepo;
    private final CommentRepo commentRepo;
    private final CommentLikeRepository commentLikeRepository;

    public void likePost(User currentUser, Long commentId){
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()->new ResourceNotFoundException("USER", currentUser.getId()));
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("COMMENT",commentId));
        boolean exists = commentLikeRepository.existsByUserAndComment(user,comment);

        if(exists)
            throw new RuntimeException("Already liked!");

        CommentLike like = new CommentLike();
        like.setUser(user);
        like.setComment(comment);
        like.setCreatedAt(LocalDateTime.now());
        commentLikeRepository.save(like);
    }

    public void unLikePost(User currentUser, Long commentId){
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()->new ResourceNotFoundException("USER", currentUser.getId()));
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("COMMENT",commentId));
        boolean exists = commentLikeRepository.existsByUserAndComment(user,comment);
        commentLikeRepository.deleteByUserAndComment(user,comment);
    }

    public long countCommentLikes(long commentId){
        return commentLikeRepository.countByCommentId(commentId);
    }
}
