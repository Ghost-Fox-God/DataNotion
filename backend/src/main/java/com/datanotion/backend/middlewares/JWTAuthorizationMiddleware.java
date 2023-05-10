package com.datanotion.backend.middlewares;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthorizationMiddleware extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();

        // This is needed because Spring boot's API testing passes the path through the
        // path info
        if (path.equals("")) {
            path = request.getPathInfo();
        }

        // login, register, and swagger endpoints are public
        if (path.equals("/login") || path.equals("/users/register") || path.contains("swagger")
                || path.startsWith("/v3")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtBearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtBearerToken != null && jwtBearerToken.startsWith("Bearer ")) {
            try {
                String jwtToken = jwtBearerToken.substring(7);
                Algorithm algo = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algo).build();
                DecodedJWT decodedJWT = verifier.verify(jwtToken);
                String email = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("role").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                        null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("error_message", "Bearer token missing! Make sure that it starts with 'Bearer '");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
