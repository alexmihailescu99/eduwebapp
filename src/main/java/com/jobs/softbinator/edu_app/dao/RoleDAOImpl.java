package com.jobs.softbinator.edu_app.dao;

import com.jobs.softbinator.edu_app.entity.Role;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Role findByName(String name) {
        Session currSession = entityManager.unwrap(Session.class);
        Query q = currSession.createQuery("from Role r where r.name=:name");
        q.setParameter("name", name);
        List<Role> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
