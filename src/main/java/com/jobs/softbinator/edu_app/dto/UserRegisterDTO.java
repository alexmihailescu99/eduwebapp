package com.jobs.softbinator.edu_app.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private @Getter @Setter String username;
    private @Getter @Setter String password;
    private @Getter @Setter String firstName;
    private @Getter @Setter String lastName;
}
