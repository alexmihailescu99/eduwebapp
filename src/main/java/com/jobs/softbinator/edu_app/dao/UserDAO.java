package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.User;

import java.util.List;

public interface UserDAO {
    void add(User user);
    List<User> findAll();
    List<User> findAllFollowed(User user);
    List<User> findAllFollowers(User user);
    User findByUsername(String name);
    User findById(Long id);
}
