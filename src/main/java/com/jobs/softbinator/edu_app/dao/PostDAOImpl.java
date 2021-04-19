package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Post;
import com.jobs.softbinator.edu_app.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class PostDAOImpl implements PostDAO {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void add(Post post) {
        Session currSession = entityManager.unwrap(Session.class);
        currSession.save(post);
    }

    @Override
    @Transactional
    public List<Post> findAll() {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Post p order by p.postedAt asc");
        List<Post> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }


    @Override
    @Transactional
    public List<Post> findAllLike(String like) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Post p where upper(p.title) like upper(:like) order by p.postedAt asc");
        // I don't know if this would be vunerable to SQL injection
        q.setParameter("like", "%" + like + "%");
        List<Post> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }

    @Override
    @Transactional
    public List<Post> findAllFollowed(User user) {
        Session currSession = entityManager.unwrap(Session.class);
        List<User> followed = userDAO.findAllFollowed(user);
        List<Long> followedId = new ArrayList<>();
        for (User u : followed) {
            followedId.add(u.getId());
        }
        Query q = currSession.createQuery("from Post p where p.author.id in :followed order by p.postedAt asc");
        q.setParameter("followed", followedId);
        List<Post> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }

    @Override
    @Transactional
    public Post findById(Long id) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Post p where p.id=:id order by p.postedAt asc");
        q.setParameter("id", id);
        List<Post> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    @Transactional
    public List<Post> findByTitle(String title) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Post p where p.title=:title order by p.postedAt asc");
        q.setParameter("title", title);
        List<Post> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }

    @Override
    public List<Post> findByAuthor(User author) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Post p where p.author=:author order by p.postedAt asc");
        q.setParameter("author", author);
        List<Post> result = q.getResultList();
        return result.isEmpty() ? null : result;
    }
}