package com.jobs.softbinator.edu_app.dto;

import com.jobs.softbinator.edu_app.entity.Category;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private @Getter @Setter Long id;
    private @Getter @Setter String authorUsername;
    private @Getter @Setter String title;
    private @Getter @Setter String content;
    private @Getter @Setter String category;
    private @Getter @Setter Date postedAt;
    private @Getter @Setter Long noReplies;
}
