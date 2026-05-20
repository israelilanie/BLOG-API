package com.example.blogapi.controller;

import com.example.blogapi.model.User;
import com.example.blogapi.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-like")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@AuthenticationPrincipal User user, @PathVariable long commentId){
        commentLikeService.likePost(user,commentId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void unLike(@AuthenticationPrincipal User user, @PathVariable long commentId){
        commentLikeService.unLikePost(user,commentId);
    }

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public long countLike(@PathVariable long commentId){
        return commentLikeService.countCommentLikes(commentId);
    }
}
