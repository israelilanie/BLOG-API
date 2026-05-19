package com.example.blogapi.service;

import com.example.blogapi.dto.CommentRequestDTO;
import com.example.blogapi.dto.CommentResponseDTO;
import com.example.blogapi.exception.AccessDeniedException;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.mapper.CommentMapper;
import com.example.blogapi.mapper.PostMapper;
import com.example.blogapi.mapper.UserMapper;
import com.example.blogapi.model.Comment;
import com.example.blogapi.model.Post;
import com.example.blogapi.model.User;
import com.example.blogapi.repository.CommentRepo;
import com.example.blogapi.repository.PostRepo;
import com.example.blogapi.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentLikeService commentLikeService;

    private final UserRepo userRepo;

    private final PostRepo postRepo;

    private final AuthorizationService authorizationService;

    private final CommentRepo commentRepo;

    private final CommentMapper commentMapper;

    private final EmailService emailService;

    public CommentResponseDTO addComment(Long postId, User currentUser , CommentRequestDTO commentRequestDTO){
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
        User user = userRepo.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", currentUser.getId()));
        Comment comment = commentMapper.comment(commentRequestDTO);
        comment.setPost(post);
        comment.setAuthor(user);

        Comment comment1 = commentRepo.save(comment);
        long countComment = 0L;
        if(!authorizationService.isPostOwner(post,user)){
            emailService.sendCommentNotification(
                    user.getEmail(),
                    post.getTitle(),
                    user.getUsername()
            );
        }

        CommentResponseDTO responseDTO= commentMapper.commentResponseDTO(comment1);
        responseDTO.setLikeCount(countComment);
        return responseDTO;
    }

    public List<CommentResponseDTO> getCommentsByPost(Long postId) {

        List<CommentResponseDTO> commentResponseDTOList = commentRepo.findByPostId(postId)
                .stream()
                .map(commentMapper::commentResponseDTO)
                .toList();

        return commentResponseDTOList.stream().peek(u->u.setLikeCount(commentLikeService.countCommentLikes(u.getId()))).toList();
    }

    public void deleteComment(Long commentId, User currentUser) {
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()-> new ResourceNotFoundException("User",currentUser.getId()));
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));

        if (!authorizationService.canDeleteComment(comment,user)) {
            throw new AccessDeniedException("You cannot delete this comment");
        }

        commentRepo.delete(comment);
    }

    public CommentResponseDTO updateComment(Long commentId, User currentUser, CommentRequestDTO commentRequestDTO){
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()-> new ResourceNotFoundException("User",currentUser.getId()));
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));
        if (!authorizationService.canEditComment(comment,user)) {
            throw new AccessDeniedException("You cannot Edit this comment");
        }
        comment.setContent(commentRequestDTO.getContent());
        Comment comment1 = commentRepo.save(comment);
        long countCommentLike = commentLikeService.countCommentLikes(commentId);
        CommentResponseDTO responseDTO= commentMapper.commentResponseDTO(comment1);
        responseDTO.setLikeCount(countCommentLike);
        return responseDTO;
    }

    public Page<CommentResponseDTO> getComments(Pageable pageable){
        return commentRepo.findAll(pageable)
                .map(comment -> {

                    CommentResponseDTO dto =
                            commentMapper.commentResponseDTO(comment);

                    dto.setLikeCount(
                            commentLikeService.countCommentLikes(comment.getId())
                    );

                    return dto;
                });    }

}
