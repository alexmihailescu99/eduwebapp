package com.jobs.softbinator.edu_app.service;

import com.jobs.softbinator.edu_app.dao.*;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PostDAO postDAO;

    @Autowired
    ReplyDAO replyDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    CategoryDAO categoryDAO;

    public void add(PostDTO postDTO) {
        User currUser = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(currUser)
                .category(categoryDAO.findByTitle(postDTO.getCategory()))
                .postedAt(new Date(System.currentTimeMillis()))
                .replies(new ArrayList<Reply>())
                .build();
        postDAO.add(post);
    }

    public PostDTO findById(Long id) {
        Post post = postDAO.findById(id);
        if (post == null)
            return null;

        return PostDTO.builder()
                .id(post.getId())
                .authorUsername(post.getAuthor().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory().getTitle())
                .postedAt(post.getPostedAt())
                .build();
    }

    public List<PostDTO> findAll() {
        List<Post> fullPosts = postDAO.findAll();
        if (fullPosts == null)
            return null;

        List<PostDTO> posts = new ArrayList<>();
        for (Post p : fullPosts) {
            List<Reply> replies = replyDAO.findByPost(p);
            PostDTO postDTO = PostDTO.builder()
                    .id(p.getId())
                    .authorUsername(p.getAuthor().getUsername())
                    .title(p.getTitle())
                    .content(p.getContent())
                    .category(p.getCategory().getTitle())
                    .postedAt(p.getPostedAt())
                    .noReplies(replies == null ? 0 : (long)replies.size())
                    .build();
            posts.add(postDTO);
        }
        return posts;
    }

    public List<ReplyDTO> getReplies(PostDTO postDTO) {
        Post post = postDAO.findById(postDTO.getId());
        if (post == null)
            return null;

        List<Reply> replies = replyDAO.findByPost(post);
        if (replies == null || replies.isEmpty())
            return null;

        List<ReplyDTO> replyDTOs = new ArrayList<>();

        for (Reply reply : replies) {
            UserDTO userDTO = UserDTO.builder()
                    .id(reply.getAuthor().getId())
                    .username(reply.getAuthor().getUsername())
                    .firstName(reply.getAuthor().getFirstName())
                    .lastName(reply.getAuthor().getLastName())
                    .role(reply.getAuthor().getRoles().iterator().next().getName())
                    .email(reply.getAuthor().getEmail())
                    .occupation(reply.getAuthor().getOccupation())
                    .phoneNumber(reply.getAuthor().getPhoneNumber())
                    .build();

            ReplyDTO replyDTO = ReplyDTO.builder()
                    .id(reply.getId())
                    .author(userDTO)
                    .content(reply.getContent())
                    .postedAt(reply.getPostedAt())
                    .build();
            replyDTOs.add(replyDTO);
        }
        return replyDTOs;
    }

    public void addReply(Long postId, ReplyDTO replyDTO) {
        Post post = postDAO.findById(postId);
        User currUser = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        Reply reply = Reply.builder()
                .content(replyDTO.getContent())
                .author(currUser)
                .postedAt(new Date(System.currentTimeMillis()))
                .post(post)
                .build();
        replyDAO.add(reply);
    }

    public Boolean deleteReply(Long replyId) {
        Reply reply = replyDAO.findById(replyId);
        System.out.println(reply.getContent());
        User currUser = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        // Only the author, moderator and admins can delete a reply
        if (reply.getAuthor().getId() != currUser.getId()
                && !currUser.getRoles().contains(roleDAO.findByName("ROLE_ADMIN")))
            return false;

        replyDAO.delete(reply);
        return true;
    }
}
