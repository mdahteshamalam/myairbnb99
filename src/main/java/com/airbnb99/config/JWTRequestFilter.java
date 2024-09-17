package com.airbnb99.config;

import com.airbnb99.entity.PropertyUser;
import com.airbnb99.repository.PropertyUserRepository;
import com.airbnb99.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private PropertyUserRepository userRepository;
    private JWTService jwtService;

    public JWTRequestFilter(PropertyUserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //String tokenHeader = request.getHeader("Authentication");
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader !=null && tokenHeader.startsWith("Bearer ")){
              String token = tokenHeader.substring(8, tokenHeader.length() - 1);
          //    System.out.println(token);
              String username = jwtService.getUsername(token);
              Optional<PropertyUser> opUser = userRepository.findByUsername(username);
              if (opUser.isPresent()){
                  PropertyUser user = opUser.get();
                     // TO keep track of current user logged in
                  UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user,null, Collections.singleton(new SimpleGrantedAuthority(user.getUserRole())));
                  authentication.setDetails(new WebAuthenticationDetails(request));
                  SecurityContextHolder.getContext().setAuthentication(authentication);
              }

          }
        filterChain.doFilter(request,response);
    }


}
