package aar.example.auth.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import aar.example.auth.jwt.JwtTokenUtil;

public class JWTFilter extends OncePerRequestFilter {

    private String tokenHeader = "TOKEN";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

    	String authToken = request.getHeader(this.tokenHeader);  
    	
    	if (authToken != null) {
    		String username = jwtTokenUtil.getUsernameFromToken(authToken);

    		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

    			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

    			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
    				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));                 
    				SecurityContextHolder.getContext().setAuthentication(authentication);
    			}
    		}
    	}
         chain.doFilter(request, response);  
    }
}