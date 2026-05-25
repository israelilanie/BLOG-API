package com.example.blogapi.mapper;

import com.example.blogapi.dto.*;
import com.example.blogapi.model.Comment;
import com.example.blogapi.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {

    @Autowired
    CommentMapper commentMapper;

    public PostResponseDTO postResponseDTO(Post post){
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        UserSummaryDTO userSummaryDTO = new UserSummaryDTO();
        postResponseDTO.setId(post.getId());
        postResponseDTO.setTitle(post.getTitle());
        postResponseDTO.setContent(post.getContent());
        postResponseDTO.setPublished(post.getPublished());

        if(post.getImageName() != null){
            postResponseDTO.setImageUrl( "http://localhost:8080/images/"
                    + post.getImageName());
        }


        if (post.getAuthor() != null) {
            UserSummaryDTO user = new UserSummaryDTO();
            user.setId(post.getAuthor().getId());
            user.setUsername(post.getAuthor().getUsername());
            postResponseDTO.setAuthor(user);
        }
        return postResponseDTO;
    }

    public PostDetailDTO postDetailDTO(Post post){
        PostDetailDTO postDetailDTO = new PostDetailDTO();
        UserSummaryDTO userSummaryDTO = new UserSummaryDTO();
        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        postDetailDTO.setId(post.getId());
        postDetailDTO.setTitle(post.getTitle());
        postDetailDTO.setSlug(post.getSlug());
        postDetailDTO.setCreatedAt(post.getCreatedAt());
        postDetailDTO.setUpdatedAt(post.getUpdatedAt());
        postDetailDTO.setCommentCount(post.getComments().size());

        for(Comment comment : post.getComments()){
            CommentResponseDTO commentResponseDTO = commentMapper.commentResponseDTO(comment);
            commentResponseDTOList.add(commentResponseDTO);
        }
        postDetailDTO.setComments(commentResponseDTOList);
        return postDetailDTO;
    }

    public Post post(PostRequestDTO postRequestDTO){
        Post post = new Post();
        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        post.setPublished(postRequestDTO.isPublished());
        post.setSlug(postRequestDTO.getSlug());
        post.setCreatedAt(LocalDateTime.now());
        return post;
    }
}
