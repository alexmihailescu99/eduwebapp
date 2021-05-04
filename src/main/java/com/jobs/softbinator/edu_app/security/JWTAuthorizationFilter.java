package com.jobs.softbinator.edu_app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);
        // If the header does not exist or it doesn't start with Bearer
        if (header == null) {
            System.out.println("Header is null: " + header);
            // Move on
            chain.doFilter(req, res);
            return;
        }

        if (!header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            System.out.println("Header does not start with " + SecurityConstants.TOKEN_PREFIX);
            // Move on
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            System.out.println("Token: " + token);
            // Parse the token
            String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY.getBytes()))
                    .build()
                    .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();
            System.out.println("User Authorization: " + user);
            if (user != null) {
                System.out.println("Successful!");
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            } else System.out.println("Failed!");
            return null;
        }
        return null;
    }
}
