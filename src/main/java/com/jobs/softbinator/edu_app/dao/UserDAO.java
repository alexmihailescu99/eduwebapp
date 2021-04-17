package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.User;

public interface UserDAO {
    public void add(User user);
    public User findByUsername(String name);
    public User findById(Long id);
}
