package com.jobs.softbinator.edu_app;

import com.jobs.softbinator.edu_app.service.FollowService;
import com.jobs.softbinator.edu_app.service.PostService;
import com.jobs.softbinator.edu_app.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EduAppApplication {

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	PostService postService() { return new PostService(); }

	@Bean
	UserService userService() { return new UserService(); }

	@Bean
	FollowService followService() { return new FollowService(); }

	public static void main(String[] args) {
		SpringApplication.run(EduAppApplication.class, args);
	}

}
