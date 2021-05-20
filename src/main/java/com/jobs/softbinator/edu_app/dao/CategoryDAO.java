package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Category;

import java.util.List;

public interface CategoryDAO {
    public List<Category> findAll();
    public Category findById(Long id);
    public Category findByTitle(String title);
}
