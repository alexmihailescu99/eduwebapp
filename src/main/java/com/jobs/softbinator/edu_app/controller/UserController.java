package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dao.RoleDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.UserRegisterDTO;
import com.jobs.softbinator.edu_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public ResponseEntity<String> register(UserRegisterDTO userCredentials) {
        User newUser = User.builder()
                .username(userCredentials.getUsername())
                .password(bCryptPasswordEncoder.encode(userCredentials.getPassword()))
                .firstName(userCredentials.getFirstName())
                .lastName(userCredentials.getLastName())
                .roles(Arrays.asList(roleDAO.findByName("USER")))
                .build();
        userDAO.add(newUser);

        return new ResponseEntity<>("Successfully added user " + newUser.getUsername(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return new ResponseEntity<>((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal(), HttpStatus.OK);
    }
}
