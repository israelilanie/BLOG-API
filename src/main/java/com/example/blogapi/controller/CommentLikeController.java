package com.example.blogapi.controller;

import com.example.blogapi.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-like")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{userId}/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@PathVariable long userId, @PathVariable long commentId){
        commentLikeService.likePost(userId,commentId);
    }

    @DeleteMapping("/{userId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void unLike(@PathVariable long userId, @PathVariable long commentId){
        commentLikeService.unLikePost(userId,commentId);
    }

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public long countLike(@PathVariable long commentId){
        return commentLikeService.countCommentLikes(commentId);
    }
}
