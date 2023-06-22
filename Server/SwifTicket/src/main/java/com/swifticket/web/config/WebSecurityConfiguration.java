package com.swifticket.web.config;

import com.swifticket.web.models.entities.User;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.utils.JWTTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final PasswordEncoder passwordEncoder;

    private final UserServices userService;

    private final JWTTokenFilter filter;
    @Autowired
    public WebSecurityConfiguration(PasswordEncoder passwordEncoder, UserServices userService, JWTTokenFilter filter) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.filter = filter;
    }

    @Bean
    AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder managerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        managerBuilder
                .userDetailsService(identifier -> {
                    System.out.println("ID: " + identifier);
                    User user = userService.findOneByEmail(identifier);

                    if(user == null) {
                        System.out.println("User: " + identifier + ", not found!");
                        throw new UsernameNotFoundException("User: " + identifier + ", not found!");
                    }

                    return user;
                })
                .passwordEncoder(passwordEncoder);

        return managerBuilder.build();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Http login and cors disabled
        http.httpBasic(withDefaults()).csrf(csrf -> csrf.disable());

        //Route filter
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers("/**").permitAll() // RULE TO ALLOW ALL
                        /*
                        .requestMatchers(HttpMethod.GET,"/auth/validate-token").authenticated()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/email/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/system/system-state").permitAll()
                        .requestMatchers(HttpMethod.GET,"/events/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/organizers").permitAll()
                        .requestMatchers(HttpMethod.GET,"/sponsors/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/categories").permitAll()
                        .requestMatchers(HttpMethod.GET,"/places/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/tickets/transfer/validate-transfer/**").permitAll()
                         */
                        .anyRequest().authenticated()
        );

        //Statelessness
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //UnAuthorized handler
        http.exceptionHandling(handling -> handling.authenticationEntryPoint((req, res, ex) -> {
            res.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Auth fail!"
            );
        }));

        //JWT filter
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
