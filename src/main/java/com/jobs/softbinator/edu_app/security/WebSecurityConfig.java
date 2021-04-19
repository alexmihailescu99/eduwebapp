package com.jobs.softbinator.edu_app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // Allow register
                .antMatchers("/api/user").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                // Add our custom authentication & authorization filters
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .exceptionHandling()
                .authenticationEntryPoint(new JWTAuthorizationFailureHandler())
                .and()
                // Disable session creation
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
