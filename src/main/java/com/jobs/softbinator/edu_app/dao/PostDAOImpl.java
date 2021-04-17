package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PostDAOImpl implements PostDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void add(Post post) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.save(post);
    }

    @Override
    public Post findById(Long id) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Post p where p.id=:id");
        q.setParameter("id", id);
        List<Post> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    @Transactional
    public List<Post> findByTitle(String title) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Post p where p.title=:title");
        q.setParameter("title", title);
        List<Post> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }

    @Override
    public List<Post> findByAuthor(User author) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Post p where p.author=:author");
        q.setParameter("author", author);
        List<Post> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }
}
