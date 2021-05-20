package com.jobs.softbinator.edu_app.service;

import com.jobs.softbinator.edu_app.dao.CategoryDAO;
import com.jobs.softbinator.edu_app.dao.PostDAO;
import com.jobs.softbinator.edu_app.dao.ReplyDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.PostDTO;
import com.jobs.softbinator.edu_app.entity.Category;
import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.Reply;
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

    @Autowired
    ReplyDAO replyDAO;

    @Autowired
    CategoryDAO categoryDAO;

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

    public List<Category> findAllCategories() {
        List<Category> categories = categoryDAO.findAll();
        return (categories != null && !categories.isEmpty()) ? categories : null;
    }

    public Boolean addFollowedCategory(String category) {
        Category cat = categoryDAO.findByTitle(category);
        System.out.println(cat.getTitle());
        if (cat == null)
            return false;

        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        if (user == null)
            return false;

        // If the user is already following, unfollow
        if (user.getFollowedCategories().contains(cat)) {
            user.getFollowedCategories().remove(cat);
            userDAO.add(user);
            return true;
        }

        user.getFollowedCategories().add(cat);
        userDAO.add(user);

        return true;
    }

    public List<Category> getFollowedCategories() {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        if (user == null || user.getFollowedCategories() == null || user.getFollowedCategories().isEmpty())
            return null;

        return user.getFollowedCategories();
    }
}
