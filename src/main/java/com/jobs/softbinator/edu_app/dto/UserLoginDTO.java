package com.jobs.softbinator.edu_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private @Getter @Setter String username;
    private @Getter @Setter String password;
}
