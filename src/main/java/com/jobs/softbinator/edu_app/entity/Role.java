package com.jobs.softbinator.edu_app.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private @Getter @Setter Long id;

    @Column(name = "name")
    private @Getter @Setter String name;
}
