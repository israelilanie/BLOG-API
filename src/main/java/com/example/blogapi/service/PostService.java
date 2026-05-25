package com.example.blogapi.service;

import com.example.blogapi.dto.PostRequestDTO;
import com.example.blogapi.dto.PostResponseDTO;
import com.example.blogapi.exception.AccessDeniedException;
import com.example.blogapi.exception.ResourceNotFoundException;
import com.example.blogapi.mapper.PostMapper;
import com.example.blogapi.mapper.UserMapper;
import com.example.blogapi.model.Post;
import com.example.blogapi.model.User;
import com.example.blogapi.repository.PostRepo;
import com.example.blogapi.repository.UserRepo;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostLikeService postLikeService;
    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final AuthorizationService authorizationService;
    private final UserRepo userRepo;
    private final FileService fileService;

    public PostResponseDTO createPost(User currentUser,String title, String content,String slug, boolean published, MultipartFile image){

        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setTitle(title);
        postRequestDTO.setContent(content);
        postRequestDTO.setSlug(slug);
        postRequestDTO.setPublished(published);

        User user = userRepo.findById(currentUser.getId()).orElseThrow(()-> new ResourceNotFoundException("User", currentUser.getId()));
        Post post = postMapper.post(postRequestDTO);
        post.setAuthor(user);

        if(image != null && !image.isEmpty()) {

            String filename =
                    fileService.upload(image);

            post.setImageName(filename);
        }

        Post post1 = postRepo.save(post);
        long count = 0L;

        PostResponseDTO dto = postMapper.postResponseDTO(post1);
        dto.setLikeCount(count);
        return dto ;
    }

    public List<PostResponseDTO> getAllPosts(){
        List<PostResponseDTO> dtoList = postRepo.findAll().stream().map(postMapper::postResponseDTO).toList();
        return dtoList.stream().peek(u->u.setLikeCount(postLikeService.countPostLike(u.getId()))).toList();
    }

    public PostResponseDTO updatePost(Long postId, User currentUser, PostRequestDTO postRequestDTO){
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()->new ResourceNotFoundException("USER", currentUser.getId()));

        if(!authorizationService.canEditPost(post,user)){
            throw new AccessDeniedException("You cannot edit this post");
        }

        post.setTitle(postRequestDTO.getTitle());
        post.setSlug(postRequestDTO.getSlug());
        post.setContent(postRequestDTO.getContent());
        post.setUpdatedAt(LocalDateTime.now());
        post.setPublished(Boolean.TRUE);

        Post post1 = postRepo.save(post);
        long countLike = postLikeService.countPostLike(post1.getId());
        PostResponseDTO dto = postMapper.postResponseDTO(post1);
        dto.setLikeCount(countLike);
        return dto;
    }

    public void deletePost(Long postId, User currentUser){
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
        User user = userRepo.findById(currentUser.getId()).orElseThrow(()->new ResourceNotFoundException("USER", currentUser.getId()));

        if (!authorizationService.canDeletePost(post,user)) {
            throw new AccessDeniedException("You cannot delete this post");
        }
        postRepo.delete(post);
    }

    public PostResponseDTO getPostById(Long id){
        Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post",id));

        PostResponseDTO dto = postMapper.postResponseDTO(post);
        long countLike = postLikeService.countPostLike(post.getId());
        dto.setLikeCount(countLike);
        return dto;
    }

    public List<PostResponseDTO> getByAuthorId(Long userId){
        List<Post> post = postRepo.findByAuthorId(userId);
        List<PostResponseDTO> dtoList = postRepo.findByAuthorId(userId).stream().map(postMapper::postResponseDTO).toList();
        return dtoList.stream().peek(u->u.setLikeCount(postLikeService.countPostLike(u.getId()))).toList();
    }

    public Page<PostResponseDTO> getAllPosts(Pageable pageable){

        return postRepo.findAll(pageable)
                .map(post -> {
                    PostResponseDTO dto = postMapper.postResponseDTO(post);

                    dto.setLikeCount(
                            postLikeService.countPostLike(post.getId())
                    );

                    return dto;
                });}

    public Page<PostResponseDTO> getAllPageByMe(Pageable pageable,User currentUser){
        return postRepo.findByAuthorId(pageable, currentUser.getId()).map(postMapper::postResponseDTO);
    }

    public List<PostResponseDTO> getAllByMe(User currentUser){
        return postRepo.findByAuthorId(currentUser.getId()).stream().map(postMapper::postResponseDTO).toList();
    }
}
