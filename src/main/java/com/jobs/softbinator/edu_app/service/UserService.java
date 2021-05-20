package com.jobs.softbinator.edu_app.service;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void add(UserRegisterDTO userCredentials, String role) {
        User user = User.builder()
                .username(userCredentials.getUsername())
                .password(bCryptPasswordEncoder.encode(userCredentials.getPassword()))
                .firstName(userCredentials.getFirstName())
                .lastName(userCredentials.getLastName())
                .roles(Arrays.asList(roleDAO.findByName(role)))
                .email(userCredentials.getEmail())
                .occupation(userCredentials.getOccupation())
                .phoneNumber(userCredentials.getPhoneNumber())
                .build();
        userDAO.add(user);
    }

    public Boolean update(UserDTO userDTO) {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());

        // If the user does not exist or someone other than the user or admin wants to modify the profile
        if (user == null || !userDTO.getUsername().equals(user.getUsername()))
            return false;

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setOccupation(userDTO.getOccupation());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userDAO.add(user);
        return true;
    }

    public List<UserDTO> findAll() {
        List<User> users = userDAO.findAll();
        if (users == null || users.isEmpty())
            return null;

        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
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
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public UserDTO test() {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        if (user == null)
            return null;

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRoles().iterator().next().getName())
                .email(user.getEmail())
                .occupation(user.getOccupation())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public UserDTO findByUsername(String username) {
        User user = userDAO.findByUsername(username);
        if (user == null)
            return null;

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRoles().iterator().next().getName())
                .email(user.getEmail())
                .occupation(user.getOccupation())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public Boolean addFollower(Long followedId) {
        User follower = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        User followed = userDAO.findById(followedId);

        if (follower == null || followed == null)
            return false;

        // If the user wants to follow themselves
        if (followedId.equals(follower.getId()))
            return false;

        // If the user is already following the person, remove it
        // Sort of like a trigger
        if (followed.getFollowers().contains(follower)) {
            followed.getFollowers().remove(follower);
            follower.getFollowed().remove(followed);
            userDAO.add(followed);
            userDAO.add(follower);
            return true;
        }

        followed.getFollowers().add(follower);
        follower.getFollowed().add(followed);

        // Update the users in the database
        userDAO.add(followed);
        userDAO.add(follower);
        return true;
    }

    public List<UserDTO> getFollowers() {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        if (user == null)
            return null;

        List<User> followers = user.getFollowers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User follower : followers) {
            UserDTO currUser = UserDTO.builder()
                    .id(follower.getId())
                    .firstName(follower.getFirstName())
                    .lastName(follower.getLastName())
                    .username(follower.getUsername())
                    .role(follower.getRoles().iterator().next().getName())
                    .email(follower.getEmail())
                    .occupation(follower.getOccupation())
                    .phoneNumber(follower.getPhoneNumber())
                    .build();
            userDTOs.add(currUser);
        }
        return userDTOs;
    }

    public List<UserDTO> getFollowed() {
        User user = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        if (user == null)
            return null;

        List<User> followed = user.getFollowed();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User follower : followed) {
            UserDTO currUser = UserDTO.builder()
                    .id(follower.getId())
                    .firstName(follower.getFirstName())
                    .lastName(follower.getLastName())
                    .username(follower.getUsername())
                    .role(follower.getRoles().iterator().next().getName())
                    .email(follower.getEmail())
                    .occupation(follower.getOccupation())
                    .phoneNumber(follower.getPhoneNumber())
                    .build();
            userDTOs.add(currUser);
        }
        return userDTOs;
    }

    public Boolean checkIfFollowed(Long followedId) {
        User follower = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        if (follower == null)
            return false;

        User followed = userDAO.findById(followedId);
        if (followed == null)
            return false;

        return follower.getFollowed().contains(followed);
    }
}
