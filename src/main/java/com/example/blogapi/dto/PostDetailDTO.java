package com.example.blogapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDTO {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private UserSummaryDTO author;
    private List<CommentResponseDTO> comments;
    private long commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long likeCount;
}
