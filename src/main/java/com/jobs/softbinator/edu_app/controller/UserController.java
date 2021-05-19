package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dto.UserDTO;
import com.jobs.softbinator.edu_app.dto.UserRegisterDTO;
import com.jobs.softbinator.edu_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userCredentials) {
        userService.add(userCredentials);
        return new ResponseEntity<>("Successfully added user " + userCredentials.getUsername(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        // If the user does not exist or someone other than the user wants to modify the profile, return UNAUTHORIZED
        return (userService.update(userDTO)) ? new ResponseEntity<>("Successfully updated user " + userDTO.getUsername(), HttpStatus.OK)
                : new ResponseEntity<>("You may not modify this profile", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<UserDTO> testUser() {
        UserDTO currUser = userService.test();
        return (currUser != null) ? new ResponseEntity<>(currUser, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> userDetails(@PathVariable String username) {
        UserDTO userDTO = userService.findByUsername(username);
        return (userDTO != null) ? new ResponseEntity<>(userDTO, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/follow/{followedId}")
    public ResponseEntity<String> addFollower(@PathVariable Long followedId) {
        return (userService.addFollower(followedId)) ? new ResponseEntity<>("Follower added successfully", HttpStatus.OK)
                : new ResponseEntity<>("Something bad happened", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/follower")
    public ResponseEntity<List<UserDTO>> followers() {
        List<UserDTO> followers = userService.getFollowers();
        return (followers != null && !(followers.isEmpty())) ? new ResponseEntity<>(followers, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/followed")
    public ResponseEntity<List<UserDTO>> followed() {
        List<UserDTO> followed = userService.getFollowed();
        return (followed != null && !(followed.isEmpty())) ? new ResponseEntity<>(followed, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/followed/{followedId}")
    public ResponseEntity<String> checkIfFollowed(@PathVariable Long followedId) {
        return (userService.checkIfFollowed(followedId)) ? new ResponseEntity<>("true", HttpStatus.OK)
                : new ResponseEntity<>("false", HttpStatus.NOT_FOUND);
    }
}
