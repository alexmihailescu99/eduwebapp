package com.jobs.softbinator.edu_app.security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationToken {
    private @Getter @Setter String token;
}
