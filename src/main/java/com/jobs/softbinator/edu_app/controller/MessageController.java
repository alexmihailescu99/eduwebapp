package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dao.MessageDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.MessageDTO;
import com.jobs.softbinator.edu_app.entity.Message;
import com.jobs.softbinator.edu_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserDAO userDAO;

    @PostMapping
    public ResponseEntity<String> send(@RequestBody MessageDTO message) {
        User sender = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        User receiver = userDAO.findById(message.getReceiverId());
        if (message.getSenderId() <= 0 || sender == null)
            return new ResponseEntity<>("Bad sender id\n", HttpStatus.BAD_REQUEST);
        if (message.getReceiverId() <= 0 || receiver == null)
            return new ResponseEntity<>("Bad receiver id\n", HttpStatus.BAD_REQUEST);
        if (message.getContent().isEmpty())
            return new ResponseEntity<>("Message is empty\n", HttpStatus.BAD_REQUEST);

        Message newMessage = Message.builder()
                .content(message.getContent())
                .sender(sender)
                .receiver(userDAO.findById(message.getReceiverId()))
                .sentAt(new Date(System.currentTimeMillis()))
                .build();
        messageDAO.add(newMessage);

        return new ResponseEntity<>( "Message sent", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ArrayList<MessageDTO>> getDiscussion(@RequestParam Long receiverId) {
        User currUser = userDAO.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Long senderId = currUser.getId();
        // Allow only the user to access their own messages
        if (currUser.getId() != senderId && currUser.getId() != receiverId) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        User sender = userDAO.findById(senderId), receiver = userDAO.findById(receiverId);
        ArrayList<Message> messages = (ArrayList<Message>)messageDAO.findDiscussion(sender, receiver);
        if (messages == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        // Transform them into DTOs
        ArrayList<MessageDTO> messagesToBeTransmitted = new ArrayList<>();
        for (Message m : messages) {
            MessageDTO msg = MessageDTO.builder()
                    .senderId(m.getSender().getId())
                    .receiverId(m.getReceiver().getId())
                    .content(m.getContent())
                    .sentAt(m.getSentAt())
                    .build();
            messagesToBeTransmitted.add(msg);
        }

        return new ResponseEntity<>(messagesToBeTransmitted, HttpStatus.OK);
    }
}
