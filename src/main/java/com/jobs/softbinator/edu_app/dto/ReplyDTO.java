package com.jobs.softbinator.edu_app.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
    private @Getter @Setter String content;
}