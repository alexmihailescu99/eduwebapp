package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Message;
import com.jobs.softbinator.edu_app.entity.User;

import java.util.List;

public interface MessageDAO {
    public void add(Message message);
    public List<Message> findBySender(User sender);
    public List<Message> findByReceiver(User receiver);
    public List<Message> findDiscussion(User sender, User receiver);
}
