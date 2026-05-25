package com.example.blogapi.controller;

import com.example.blogapi.dto.PostRequestDTO;
import com.example.blogapi.dto.PostResponseDTO;
import com.example.blogapi.model.User;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Validated
@Tag(name = "Post Controller", description = "Manage blog posts")
public class PostController {

    private final PostService postService;


    @Operation(summary = "Create a new post")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "201", description = "Post created"),
            @ApiResponse(responseCode = "404",description = "User not found")}
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDTO> createPost(@AuthenticationPrincipal User user , @RequestParam String title, @RequestParam String slug, @RequestParam String content, @RequestParam boolean published, @RequestParam(required = false) MultipartFile image) {
            PostResponseDTO postResponseDTO = postService.createPost(user,title,content,slug,published,image);
        return  ResponseEntity.status(HttpStatus.CREATED).body(postResponseDTO);
    }

    @Operation(summary = "Get all post")
    @ApiResponse(responseCode = "200", description = "Posts Retrieved!")
    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @Operation(summary = "Get post by ID")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "Post found!"),
            @ApiResponse(responseCode = "404",description = "Post not found!")}
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(summary = "Update own existing post")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "Post updated!"),
            @ApiResponse(responseCode = "400",description = "Bad request!"),
            @ApiResponse(responseCode = "401", description = "Can't update this post")
    }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody   PostRequestDTO postRequestDTO) {

        return ResponseEntity.ok(postService.updatePost(id, user, postRequestDTO));
    }

    @Operation(summary = "Delete own existing post ")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "Post deleted!"),
            @ApiResponse(responseCode = "400",description = "Bad request!"),
            @ApiResponse(responseCode = "401", description = "Can't delete this post")
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        postService.deletePost(id, user);

        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get the list of all existing post by user id")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "list retrieved!"),
            @ApiResponse(responseCode = "401", description = "Can't see this List! Unauthorized!")
    }
    )
    @GetMapping("/{userId}/user")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(postService.getByAuthorId(userId));
    }

    @Operation(summary = "Page of Posts")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "Posts Page retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad Request!")
    }
    )
    @GetMapping
    public ResponseEntity<Page<PostResponseDTO>> getPosts(Pageable pageable){
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @Operation(summary = "Own Page of Posts")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "Posts Page retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad Request!")
    }
    )
    @GetMapping("/me/page")
    public ResponseEntity<Page<PostResponseDTO>> getOwnPosts(Pageable pageable, @AuthenticationPrincipal User user ){
        return ResponseEntity.ok(postService.getAllPageByMe(pageable,user));
    }

    @Operation(summary = "Own List of Posts")
    @ApiResponses( value =  {
            @ApiResponse(responseCode = "200", description = "Posts List retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad Request!")
    }
    )
    @GetMapping("/me/list")
    public ResponseEntity<List<PostResponseDTO>> getOwnPostsList(@AuthenticationPrincipal User user ){
        return ResponseEntity.ok(postService.getAllByMe(user));
    }

}
