package com.example.blogapi.service;

import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.model.Post;
import com.example.blogapi.model.PostLike;
import com.example.blogapi.model.User;
import com.example.blogapi.repository.PostLikeRepository;
import com.example.blogapi.repository.PostRepo;
import com.example.blogapi.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final PostLikeRepository postLikeRepository;

    public void likePost(User currentUser, Long postId){
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()->new ResourceNotFoundException("USER", currentUser.getId()));
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("POST", postId));

        boolean exists = postLikeRepository.existsByUserAndPost(user,post);

        if(exists)
            throw new RuntimeException("Already liked!");

        PostLike postLike = new PostLike();
        postLike.setUser(user);
        postLike.setPost(post);
        postLike.setCreatedAt(LocalDateTime.now());
        postLikeRepository.save(postLike);
    }

    public void unLikePost(User currentUser, Long postId){
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()->new ResourceNotFoundException("USER", currentUser.getId()));
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("POST", postId));

        boolean exists = postLikeRepository.existsByUserAndPost(user,post);
        postLikeRepository.deleteByUserAndPost(user,post);
    }

    public long countPostLike(long postId){
        return postLikeRepository.countByPostId(postId);
    }
}
