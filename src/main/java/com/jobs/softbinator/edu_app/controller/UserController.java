package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.UserRegisterDTO;
import com.jobs.softbinator.edu_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public ResponseEntity<String> register(UserRegisterDTO userCredentials) {
        User newUser = User.builder()
                .username(userCredentials.getUsername())
                .password(bCryptPasswordEncoder.encode(userCredentials.getPassword()))
                .firstName(userCredentials.getFirstName())
                .lastName(userCredentials.getLastName())
                .build();
        userDAO.add(newUser);

        return new ResponseEntity<>("Sucessfully added user " + newUser.getUsername(), HttpStatus.OK);
    }
}
