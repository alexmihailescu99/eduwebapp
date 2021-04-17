package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Role;

public interface RoleDAO {
    Role findByName(String name);
}
