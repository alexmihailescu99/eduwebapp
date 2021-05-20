package com.jobs.softbinator.edu_app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @Getter @Setter Long id;

    @ManyToOne
    @JoinColumn(name = "sender")
    private @Getter @Setter User sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private @Getter @Setter User receiver;

    @Column(name = "body")
    private @Getter @Setter String body;

    @Column(name = "sent_at")
    private @Getter @Setter Date sentAt;
}
