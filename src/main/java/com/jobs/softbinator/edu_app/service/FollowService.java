package com.jobs.softbinator.edu_app.service;

import com.jobs.softbinator.edu_app.dao.PostDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.PostDTO;
import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class FollowService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    public List<PostDTO> getFrontPage() {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        List<Post> followedPosts = postDAO.findAllFollowed(user);
        if (followedPosts == null)
            return null;

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

        return posts;
    }
}
