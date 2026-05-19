package com.example.blogapi.mapper;

import com.example.blogapi.dto.CommentRequestDTO;
import com.example.blogapi.dto.CommentResponseDTO;
import com.example.blogapi.dto.UserSummaryDTO;
import com.example.blogapi.model.Comment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    public CommentResponseDTO commentResponseDTO(Comment comment){
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        UserSummaryDTO userSummaryDTO = new UserSummaryDTO();
        commentResponseDTO.setId(comment.getId());
        commentResponseDTO.setContent(comment.getContent());
        userSummaryDTO.setId(comment.getAuthor().getId());
        userSummaryDTO.setUsername(comment.getAuthor().getUsername());
        commentResponseDTO.setAuthor(userSummaryDTO);
        return commentResponseDTO;
    }

    public Comment comment(CommentRequestDTO commentRequestDTO){
        Comment comment = new Comment();
        comment.setContent(commentRequestDTO.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }
}
