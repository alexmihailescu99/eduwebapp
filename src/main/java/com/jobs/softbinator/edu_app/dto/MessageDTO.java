package com.jobs.softbinator.edu_app.dto;

import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private @Getter @Setter Long senderId;
    private @Getter @Setter String senderUsername;
    private @Getter @Setter Long receiverId;
    private @Getter @Setter String body;
    private @Getter @Setter Date sentAt;
}
