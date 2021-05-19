package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dao.PostDAO;
import com.jobs.softbinator.edu_app.dao.ReplyDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.PostDTO;
import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.User;
import com.jobs.softbinator.edu_app.service.FollowService;
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
    FollowService followService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getFrontPage() {
        List<PostDTO> postDTOs = followService.getFrontPage();
        return (postDTOs != null && !(postDTOs.isEmpty()) ? new ResponseEntity<>(postDTOs, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
