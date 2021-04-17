package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Reply;
import com.jobs.softbinator.edu_app.entity.User;

import java.util.List;

public interface ReplyDAO {
    void add(Reply reply);
    Reply findById(Long id);
    List<Reply> findByAuthor(User author);
}
