package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dao.RoleDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.UserDTO;
import com.jobs.softbinator.edu_app.dto.UserRegisterDTO;
import com.jobs.softbinator.edu_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userCredentials) {
        System.out.println(userCredentials.getUsername());
        User newUser = User.builder()
                .username(userCredentials.getUsername())
                .password(bCryptPasswordEncoder.encode(userCredentials.getPassword()))
                .firstName(userCredentials.getFirstName())
                .lastName(userCredentials.getLastName())
                .roles(Arrays.asList(roleDAO.findByName("USER")))
                .email(userCredentials.getEmail())
                .occupation(userCredentials.getOccupation())
                .phoneNumber(userCredentials.getPhoneNumber())
                .build();
        userDAO.add(newUser);

        return new ResponseEntity<>("Successfully added user " + newUser.getUsername(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserDTO> test() {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRoles().iterator().next().getName())
                .email(user.getEmail())
                .occupation(user.getOccupation())
                .phoneNumber(user.getPhoneNumber())
                .build();

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable String username) {
        User user = userDAO.findByUsername(username);
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRoles().iterator().next().getName())
                .email(user.getEmail())
                .occupation(user.getOccupation())
                .phoneNumber(user.getPhoneNumber())
                .build();

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/follow/{followedId}")
    public ResponseEntity<String> addFollower(@PathVariable Long followedId) {
        User follower = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        User followed = userDAO.findById(followedId);
        if (follower == null || followed == null)
            return new ResponseEntity<>("Could not find user(s)", HttpStatus.NOT_FOUND);

        if (followedId.equals(follower.getId()))
            return new ResponseEntity<>("You can not follow yourself", HttpStatus.BAD_REQUEST);

        if (followed.getFollowers().contains(follower))
            return new ResponseEntity<>("User is already followed", HttpStatus.BAD_REQUEST);

        followed.getFollowers().add(follower);
        follower.getFollowed().add(followed);
        // Update the followed user in the database
        userDAO.add(followed);
        userDAO.add(follower);
        return new ResponseEntity<>("Follower added successfully", HttpStatus.OK);
    }

    @GetMapping("/follower")
    public ResponseEntity<List<UserDTO>> getFollowers() {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        List<User> followers = user.getFollowers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User follower : followers) {
            UserDTO currUser = UserDTO.builder()
                    .id(follower.getId())
                    .firstName(follower.getFirstName())
                    .lastName(follower.getLastName())
                    .username(follower.getUsername())
                    .role(follower.getRoles().iterator().next().getName())
                    .build();
            userDTOs.add(currUser);
        }

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/followed")
    public ResponseEntity<List<UserDTO>> getFollowed() {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        List<User> followed = user.getFollowed();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User follower : followed) {
            UserDTO currUser = UserDTO.builder()
                    .id(follower.getId())
                    .firstName(follower.getFirstName())
                    .lastName(follower.getLastName())
                    .username(follower.getUsername())
                    .role(follower.getRoles().iterator().next().getName())
                    .build();
            userDTOs.add(currUser);
        }

        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }
}
