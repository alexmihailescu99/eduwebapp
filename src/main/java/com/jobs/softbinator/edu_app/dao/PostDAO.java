package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.User;

import java.util.List;

public interface PostDAO {
    void add(Post post);
    Post findById(Long id);
    List<Post> findAll();
    List<Post> findAllFollowed(User user);
    List<Post> findByAuthor(User author);
}
