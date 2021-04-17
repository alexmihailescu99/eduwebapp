package com.jobs.softbinator.edu_app.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private @Getter @Setter String title;
    private @Getter @Setter String content;
}
