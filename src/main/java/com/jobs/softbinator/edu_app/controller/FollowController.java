package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dto.PostDTO;

import com.jobs.softbinator.edu_app.entity.Category;
import com.jobs.softbinator.edu_app.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{category}")
    public ResponseEntity<String> addFollowedCategory(@PathVariable String category) {
        System.out.println(category);
        return (followService.addFollowedCategory(category)) ? new ResponseEntity<>("OK", HttpStatus.OK)
                : new ResponseEntity<>("NOT OK", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> followedCategories() {
        List<Category> followedCategories = followService.getFollowedCategories();
        return (followedCategories != null) ? new ResponseEntity<>(followedCategories, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
