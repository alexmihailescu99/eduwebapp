package com.jobs.softbinator.edu_app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @Getter @Setter Long id;

    @Column(name = "title")
    private @Getter @Setter String title;

    @Column(name = "content")
    private @Getter @Setter String content;

    @ManyToOne
    @JoinColumn(name = "author")
    private @Getter @Setter User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private @Getter @Setter List<Reply> replies;

    @Column(name = "posted_at")
    private @Getter @Setter Date postedAt;



}
