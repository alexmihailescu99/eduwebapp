package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dto.PostDTO;
import com.jobs.softbinator.edu_app.dto.ReplyDTO;
import com.jobs.softbinator.edu_app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getFrontPage() {
        List<PostDTO> posts = postService.findAll();
        return (posts != null) ? new ResponseEntity<>(posts, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        PostDTO postDTO = postService.findById(postId);
        return (postDTO != null) ? new ResponseEntity<>(postDTO, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/replies/{postId}")
    public ResponseEntity<List<ReplyDTO>> getReplies(@PathVariable Long postId) {
        PostDTO postDTO = postService.findById(postId);
        if (postDTO == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        List<ReplyDTO> replyDTOs = postService.getReplies(postDTO);
        return (replyDTOs != null && !(replyDTOs.isEmpty())) ? new ResponseEntity<>(replyDTOs, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addPost(@RequestBody PostDTO postDTO) {
        postService.add(postDTO);
        return new ResponseEntity<String>("Post added successfully", HttpStatus.OK);
    }

    @PostMapping("/replies/{postId}")
    public ResponseEntity<String> addReply(@PathVariable Long postId, @RequestBody ReplyDTO replyDTO) {
        PostDTO postDTO = postService.findById(postId);
        if (postDTO == null)
            return new ResponseEntity<>("Could not find post", HttpStatus.NOT_FOUND);

        postService.addReply(postId, replyDTO);
        return new ResponseEntity<String>("Reply added successfully", HttpStatus.OK);
    }
}
