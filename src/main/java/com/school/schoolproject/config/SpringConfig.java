package com.school.schoolproject.config;

import com.school.schoolproject.handler.RegFormSuccessHandler;
import com.school.schoolproject.service.RegFormDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringConfig {

    @Autowired
    private RegFormDetailsService regFormDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return regFormDetailsService;
    }

    @Bean
    public RegFormSuccessHandler regFormSuccessHandler() {
        return new RegFormSuccessHandler();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) {
        return security
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/register", "/register/**").permitAll()
                        .requestMatchers("/login", "/login/**").permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form->form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(regFormSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true) // destroy the current HTTP session
                        .clearAuthentication(true)  // clear the current authentication information
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .build();
    }
}
