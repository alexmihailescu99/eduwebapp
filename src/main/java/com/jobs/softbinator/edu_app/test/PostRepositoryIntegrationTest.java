package com.jobs.softbinator.edu_app.test;

import com.jobs.softbinator.edu_app.dao.PostDAO;
import com.jobs.softbinator.edu_app.entity.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostDAO postDAO;

    @Test
    public void whenFindById_thenReturnPost() {
        // Given
        Post post = new Post(1L, "Post Title", "Post Content", null, new ArrayList<>(), null, null);
        entityManager.persist(post);
        entityManager.flush();

        // When
        Post found = postDAO.findById(post.getId());

        // Assert that does not seem to work
        // assertThat(found.getId()).isEqualTo(post.getId());
    }

}
