package com.travelplanner.travelplanner_server.restservice.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.travelplanner.travelplanner_server.restservice.config.filters.urlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import com.travelplanner.travelplanner_server.model.services.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;

// The JwtRequestFilter checks if the request has a valid JWT token, if it has a valid JWT token, then it sets the authentication in context to specify that the current user is authenticated!
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    // Include Rule
//    // setup FilterRegistration: https://stackoverflow.com/questions/19825946/how-to-add-a-filter-class-in-spring-boot
//    @Bean
//    public FilterRegistrationBean headerValidatorFilter(){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new urlFilter());
//        registrationBean.addUrlPatterns("/authentication");
//        return registrationBean;
//    }
//
    // Exclude Rule
    // control exclude in OncePerRequestFilter: https://github.com/spring-projects/spring-boot/issues/7426
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        Collection<String> excludeUrlPatterns = new ArrayList<>();
//        excludeUrlPatterns.add("/noneauthentication");
//        excludeUrlPatterns.add("/signup");
//        excludeUrlPatterns.add("/login");
//        AntPathMatcher pathMatcher = new AntPathMatcher();
//        return excludeUrlPatterns.stream()
//                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        System.out.println("inside here!");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
        // User userDetails = this.userDAL.findUserByUsername(username);
        // if token is valid configure Spring Security to manually set
        // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
