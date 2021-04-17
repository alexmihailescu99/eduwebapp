package com.jobs.softbinator.edu_app.security;

// For use in proof-of-concept apps only
// There have to be better solutions to this
// Maybe environment variables
public class SecurityConstants {
    public static final String SECRET_KEY = "VERY_SECRET_KEY";
    public static final int EXPIRATION_TIME = 3600000; // 1 hour (15 min + token refresh recommended, but will do for the scope of the project)
    public static final String TOKEN_PREFIX ="Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
