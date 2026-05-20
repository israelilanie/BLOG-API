package com.example.blogapi.controller;

import com.example.blogapi.model.User;
import com.example.blogapi.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-like")
@RequiredArgsConstructor
@Validated
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@AuthenticationPrincipal User user, @PathVariable Long postId){
        postLikeService.likePost(user,postId);
    }

    @DeleteMapping("{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void unLike(@AuthenticationPrincipal User user, @PathVariable Long postId){
        postLikeService.unLikePost(user,postId);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public long countPostLike(@PathVariable long postId){
        return postLikeService.countPostLike(postId);
    }
}
