package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(User user) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.save(user);
    }

    @Override
    @Transactional
    public User findByUsername(String name) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from User u where u.username=:name");
        q.setParameter("name", name);
        List<User> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from User u where u.id=:id");
        q.setParameter("id", id);
        List<User> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
