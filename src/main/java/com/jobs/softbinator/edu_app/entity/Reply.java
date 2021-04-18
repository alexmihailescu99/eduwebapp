package com.jobs.softbinator.edu_app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "replies")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @Getter
    @Setter
    Long id;

    @Column(name = "content")
    private @Getter @Setter String content;

    @ManyToOne
    @JoinColumn(name = "author")
    private @Getter @Setter User author;

    @ManyToOne
    @JoinColumn(name = "post")
    private @Getter @Setter Post post;

    @Column(name = "posted_at")
    private @Getter @Setter Date postedAt;
}
