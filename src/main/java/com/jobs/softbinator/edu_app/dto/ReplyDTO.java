package com.jobs.softbinator.edu_app.dto;

import com.jobs.softbinator.edu_app.entity.User;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    private @Getter @Setter Long id;
    private @Getter @Setter String content;
    private @Getter @Setter UserDTO author;
    private @Getter @Setter Date postedAt;
}