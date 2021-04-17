package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.User;

public interface UserDAO {
    void add(User user);
    User findByUsername(String name);
    User findById(Long id);
}
