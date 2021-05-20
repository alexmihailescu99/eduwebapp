package com.jobs.softbinator.edu_app.service;

import com.jobs.softbinator.edu_app.dao.MessageDAO;
import com.jobs.softbinator.edu_app.dao.UserDAO;
import com.jobs.softbinator.edu_app.dto.MessageDTO;
import com.jobs.softbinator.edu_app.entity.Message;
import com.jobs.softbinator.edu_app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;

// Returns a code
public class MessageService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    MessageDAO messageDAO;

    public Integer send(MessageDTO message) {
        User sender = userDAO.findByUsername((String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
        User receiver = userDAO.findById(message.getReceiverId());

        if (message.getReceiverId() <= 0 || message.getBody().isEmpty() || receiver == null )
            return 400;

        Message newMessage = Message.builder()
                .body(message.getBody())
                .sender(sender)
                .receiver(userDAO.findById(message.getReceiverId()))
                .sentAt(new Date(System.currentTimeMillis()))
                .build();
        messageDAO.add(newMessage);

        return 200;
    }

    public ArrayList<MessageDTO> getDiscussion(String receiverName) {
        User currUser = userDAO.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Long senderId = currUser.getId();
        User receiver = userDAO.findByUsername(receiverName);

        // Allow only the user to access their own messages
        if (!currUser.getUsername().equals(receiverName) && !currUser.getId().equals(senderId)) {
            return null;
        }

        User sender = userDAO.findById(senderId);
        ArrayList<Message> messages = (ArrayList<Message>)messageDAO.findDiscussion(sender, receiver);
        if (messages == null)
            return new ArrayList<>();

        // Transform them into DTOs
        ArrayList<MessageDTO> messagesToBeTransmitted = new ArrayList<>();
        for (Message m : messages) {
            MessageDTO msg = MessageDTO.builder()
                    .senderId(m.getSender().getId())
                    .senderUsername(m.getSender().getUsername())
                    .receiverId(m.getReceiver().getId())
                    .body(m.getBody())
                    .sentAt(m.getSentAt())
                    .build();
            messagesToBeTransmitted.add(msg);
        }
        return messagesToBeTransmitted;
    }
}
