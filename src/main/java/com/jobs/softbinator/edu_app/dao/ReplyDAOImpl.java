package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.Reply;
import com.jobs.softbinator.edu_app.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(Reply reply) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.save(reply);
    }

    @Override
    @Transactional
    public void delete(Reply reply) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.delete(reply);
    }

    @Override
    @Transactional
    public List<Reply> findByPost(Post post) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Reply r where r.post=:post");
        q.setParameter("post", post);
        List<Reply> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }

    @Override
    @Transactional
    public Reply findById(Long id) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Reply r where r.id=:id");
        q.setParameter("id", id);
        List<Reply> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    @Transactional
    public List<Reply> findByAuthor(User author) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Reply r where r.author=:author");
        q.setParameter("author", author);
        List<Reply> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }
}
