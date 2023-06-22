package com.example.springSecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfigurer {
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    AuthenticationConfiguration authenticationconfiguration;
    UserDetailsService userDetailsService;
    public void configurer(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
    @Bean
    public SecurityFilterChain configurer(HttpSecurity http) throws Exception{
        return http
                .csrf((csrf)-> csrf.disable())
                .authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests.requestMatchers(("/login")).permitAll())
                .authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests.requestMatchers(("/Welcome")).hasAnyAuthority("admin"))
                .authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests.requestMatchers(("/Welcome")).hasAnyAuthority("Student"))
                .authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests.requestMatchers(("/Welcome")).hasAnyAuthority("Lecturer"))
                .authorizeHttpRequests((authorizeHttpRequests)->authorizeHttpRequests.anyRequest().authenticated())
    }
}
