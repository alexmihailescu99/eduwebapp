package com.jobs.softbinator.edu_app.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @Getter
    @Setter
    Long id;

    @Column(name = "title")
    private @Getter @Setter String title;
}
