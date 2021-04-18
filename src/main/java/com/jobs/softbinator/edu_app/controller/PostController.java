package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dao.PostDAO;
import com.jobs.softbinator.edu_app.dao.ReplyDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.PostDTO;
import com.jobs.softbinator.edu_app.dto.ReplyDTO;
import com.jobs.softbinator.edu_app.dto.UserDTO;
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
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<PostDTO>> getFrontPage() {
        List<Post> fullPosts = postDAO.findAll();
        if (fullPosts == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        List<PostDTO> posts = new ArrayList<>();
        for (Post p : fullPosts) {
            PostDTO postDTO = PostDTO.builder()
                    .id(p.getId())
                    .authorUsername(p.getAuthor().getUsername())
                    .title(p.getTitle())
                    .content(p.getContent())
                    .postedAt(p.getPostedAt())
                    .build();
            posts.add(postDTO);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<ReplyDTO>> getReplies(@PathVariable Long postId) {
        Post post = postDAO.findById(postId);
        if (post == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        List<Reply> replies = replyDAO.findByPost(post);
        List<ReplyDTO> replyDTOs = new ArrayList<>();

        for (Reply reply : replies) {
            UserDTO userDTO = UserDTO.builder()
                    .id(reply.getAuthor().getId())
                    .username(reply.getAuthor().getUsername())
                    .firstName(reply.getAuthor().getFirstName())
                    .lastName(reply.getAuthor().getLastName())
                    .role(reply.getAuthor().getRoles().iterator().next().getName())
                    .build();

            ReplyDTO replyDTO = ReplyDTO.builder()
                    .author(userDTO)
                    .content(reply.getContent())
                    .postedAt(reply.getPostedAt())
                    .build();
            replyDTOs.add(replyDTO);
        }

        return new ResponseEntity<>(replyDTOs, HttpStatus.OK);
    }



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
        Post post = postDAO.findById(postId);
        if (post == null)
            return new ResponseEntity<>("Could not find post", HttpStatus.NOT_FOUND);

        User currUser = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        Reply newReply = Reply.builder()
                .content(reply.getContent())
                .author(currUser)
                .postedAt(new Date(System.currentTimeMillis()))
                .post(post)
                .build();
        replyDAO.add(newReply);

        return new ResponseEntity<String>("Reply added successfully", HttpStatus.OK);
    }
}
