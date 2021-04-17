package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dao.PostDAO;
import com.jobs.softbinator.edu_app.dao.ReplyDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.PostDTO;
import com.jobs.softbinator.edu_app.dto.ReplyDTO;
import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.Reply;
import com.jobs.softbinator.edu_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    ReplyDAO replyDAO;

    @PostMapping
    public ResponseEntity<String> addPost(PostDTO post) {
        User currUser = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        Post newPost = Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(currUser)
                .postedAt(new Date(System.currentTimeMillis()))
                .replies(new ArrayList<Reply>())
                .build();
        postDAO.add(newPost);

        return new ResponseEntity<String>("Post added successfully", HttpStatus.OK);
    }
    @PostMapping("/{postId}")
    public ResponseEntity<String> addReply(@PathVariable Long postId, ReplyDTO reply) {
        User currUser = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        Reply newReply = Reply.builder()
                .content(reply.getContent())
                .author(currUser)
                .postedAt(new Date(System.currentTimeMillis()))
                .post(postDAO.findById(postId))
                .build();
        replyDAO.add(newReply);

        return new ResponseEntity<String>("Reply added successfully", HttpStatus.OK);
    }
}
