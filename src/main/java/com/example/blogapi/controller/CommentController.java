package com.example.blogapi.controller;

import com.example.blogapi.dto.CommentRequestDTO;
import com.example.blogapi.dto.CommentResponseDTO;
import com.example.blogapi.mapper.CommentMapper;
import com.example.blogapi.model.User;
import com.example.blogapi.service.CommentService;
import com.example.blogapi.service.PostService;
import com.example.blogapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Validated
@Tag(name = "Comment Controller", description = "Manage blog comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Create a new comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created!"),
            @ApiResponse(responseCode = "400", description = "Bad Request!")
    })
    @PostMapping("/{postId}/post")
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long postId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CommentRequestDTO commentRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.addComment(postId, user, commentRequestDTO));
    }

    @Operation(summary = "Get a list of comment by post ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Found!"),
            @ApiResponse(responseCode = "400", description = "Bad request!"),
            @ApiResponse(responseCode = "404", description = "Post not found!")
    })
    @GetMapping("/{postId}/post")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPost(
            @PathVariable Long postId) {

        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    @Operation(summary = "Delete comment for authenticated and authorized user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted"),
            @ApiResponse(responseCode = "400", description = "Bad Request!")
    })
    @DeleteMapping("/{commentId}/")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal User user) {

        commentService.deleteComment(commentId, user);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Updating comment for authenticated and authorized user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Updated!"),
            @ApiResponse(responseCode = "400", description = "Bad Request!")
    })
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long commentId,@AuthenticationPrincipal User user,@Valid @RequestBody CommentRequestDTO commentRequestDTO){
        return ResponseEntity.ok(commentService.updateComment(commentId,user,commentRequestDTO));
    }

    @Operation(summary = "Page of comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments page retrieve"),
            @ApiResponse(responseCode = "400", description = "Bad Request!")
    })
    @GetMapping
    public ResponseEntity<Page<CommentResponseDTO>> getComments(Pageable pageable){
        return ResponseEntity.ok(commentService.getComments(pageable));
    }

}
