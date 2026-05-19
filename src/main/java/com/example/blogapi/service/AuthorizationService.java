package com.example.blogapi.service;

import com.example.blogapi.model.Comment;
import com.example.blogapi.model.Post;
import com.example.blogapi.model.Role;
import com.example.blogapi.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public boolean isAdmin(User user){
        return user.getRole() == Role.ROLE_ADMIN;
    }

    public boolean isPostOwner(Post post, User user){
        return post.getAuthor().getId().equals(user.getId());
    }

    public boolean isCommentOwner(Comment comment, User user){
        return comment.getAuthor().getId().equals(user.getId());
    }

    public boolean canDeleteComment(Comment comment, User user){
        return isAdmin(user) || isCommentOwner(comment,user) || isPostOwner(comment.getPost(),user);
    }

    public boolean canEditComment(Comment comment, User user){
        return isCommentOwner(comment,user);
    }

    public boolean canEditPost(Post post, User user){
        return isPostOwner(post, user);
    }

    public boolean canDeletePost(Post post, User user){
        return isAdmin(user) || isPostOwner(post, user);
    }

}
