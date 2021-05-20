package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.Reply;
import com.jobs.softbinator.edu_app.entity.User;

import java.util.List;

public interface ReplyDAO {
    void add(Reply reply);
    void delete(Reply reply);
    List<Reply> findByPost(Post post);
    Reply findById(Long id);
    List<Reply> findByAuthor(User author);
}
