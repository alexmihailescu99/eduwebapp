package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dao.PostDAO;
import com.jobs.softbinator.edu_app.dao.ReplyDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.PostDTO;
import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/post/follow")
@CrossOrigin(origins = "http://localhost:3000")
public class FollowController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    ReplyDAO replyDAO;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getFrontPage() {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        List<Post> followedPosts = postDAO.findAllFollowed(user);
        if (followedPosts == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        List<PostDTO> posts = new ArrayList<>();
        for (Post p : followedPosts) {
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
}
