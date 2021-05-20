package com.jobs.softbinator.edu_app.controller;

import com.jobs.softbinator.edu_app.dto.MessageDTO;
import com.jobs.softbinator.edu_app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<String> send(@RequestBody MessageDTO message) {
        Integer status = messageService.send(message);
        if (status == 400)
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ArrayList<MessageDTO>> getDiscussion(@RequestParam String receiverName) {
        ArrayList<MessageDTO> messages = messageService.getDiscussion(receiverName);
        if (messages == null)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        if (messages.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
