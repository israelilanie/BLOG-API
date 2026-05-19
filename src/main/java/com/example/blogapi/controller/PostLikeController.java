package com.example.blogapi.controller;

import com.example.blogapi.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-like")
@RequiredArgsConstructor
@Validated
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{userId}/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@PathVariable Long userId, @PathVariable Long postId){
        postLikeService.likePost(userId,postId);
    }

    @DeleteMapping("/{userId}/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void unLike(@PathVariable Long userId, @PathVariable Long postId){
        postLikeService.unLikePost(userId,postId);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public long countPostLike(@PathVariable long postId){
        return postLikeService.countPostLike(postId);
    }
}
