package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Message;
import com.jobs.softbinator.edu_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDAOImpl implements MessageDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(Message message) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.save(message);
    }

    @Override
    @Transactional
    public List<Message> findBySender(User sender) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Message m where m.sender = :sender");
        q.setParameter("sender", sender.getId());
        List<Message> res = q.getResultList();
        return res.isEmpty() ? null : res;
    }

    @Override
    @Transactional
    public List<Message> findByReceiver(User receiver) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Message m where m.receiver = :receiver");
        q.setParameter("receiver", receiver);
        List<Message> res = q.getResultList();
        return res.isEmpty() ? null : res;
    }

    @Override
    public List<Message> findDiscussion(User sender, User receiver) {
        Session currSession = entityManager.unwrap(Session.class);
        String queryString = "from Message m where (m.sender = :sender and m.receiver = :receiver) or (m.sender = :receiver and m.receiver = :sender) order by sentAt asc ";
        Query q = currSession.createQuery(queryString);
        q.setParameter("sender", sender);
        q.setParameter("receiver", receiver);
        List<Message> res = q.getResultList();
        return res.isEmpty() ? null : res;
    }
}
