package com.akash.CareSync.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get token from request
        String requestToken = request.getHeader("Authorization");

        //Fetch user name and token
        String username = null;
        String token = null;

        if(requestToken != null && requestToken.startsWith("Bearer")){
            token = requestToken.substring(7);
            try{
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get JWT Token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT Token has expired");
            }catch (MalformedJwtException e){
                System.out.println("Invalid JWT Token");
            }
        }else{
            System.out.println("JWT token does not begin with Bearer");
        }

        //JWT token validation
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user = userDetailsService.loadUserByUsername(username);
            if(this.jwtTokenHelper.validateToken(token, user)){
                //Token is valid
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
                System.out.println("Invalid JWT Token");
            }
        }else{
            System.out.println("Username is null or context is not null");
        }
        filterChain.doFilter(request, response);
    }
}
