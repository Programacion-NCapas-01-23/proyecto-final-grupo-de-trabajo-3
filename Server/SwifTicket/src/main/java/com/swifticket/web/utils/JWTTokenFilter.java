package com.swifticket.web.utils;

import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.UserServices;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTTokenFilter extends OncePerRequestFilter {

    JWTTools jwtTools;
    UserServices userService;

    @Autowired
    public JWTTokenFilter(JWTTools jwtTools, UserServices userService) {
        this.jwtTools = jwtTools;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String ACTIVE = "Activo";
        String tokenHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(tokenHeader != null && tokenHeader.startsWith("Bearer ") && tokenHeader.length() > 7) {
            token = tokenHeader.substring(7);

            try {
                username = jwtTools.getUsernameFrom(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT TOKEN has expired");
            } catch (MalformedJwtException e) {
                System.out.println("JWT Malformado");
            }
        } else {
            System.out.println("Bearer string not found");
        }

        if(username != null && token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.findOneByEmail(username);

            if(user != null) {
                Boolean tokenValidity = userService.isTokenValid(user, token);

                // Validate if user is active and if token is valid
                if(tokenValidity && user.getState().getName().equals(ACTIVE)) {
                    //Preparing the authentication token.
                    UsernamePasswordAuthenticationToken authToken
                            = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    //This line, sets the user to security context to be handled by the filter chain
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
