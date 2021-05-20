package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Category;
import com.jobs.softbinator.edu_app.entity.Post;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Category> findAll() {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Category c order by c.title asc");
        List<Category> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }

    @Override
    public Category findById(Long id) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Category c where c.id=:id order by c.title asc");
        q.setParameter("id", id);
        List<Category> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Category findByTitle(String title) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Category c where c.title=:title order by c.title asc");
        q.setParameter("title", title);
        List<Category> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
