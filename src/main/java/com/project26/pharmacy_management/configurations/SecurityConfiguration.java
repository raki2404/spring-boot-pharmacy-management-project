package com.project26.pharmacy_management.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project26.pharmacy_management.services.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    private MyUserDetailService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/").permitAll();
            registry.requestMatchers("/medicines").permitAll();
            registry.requestMatchers("/owner/**").hasRole("OWNER");
            registry.requestMatchers("/employee/**").hasRole("EMPLOYEE");
            registry.requestMatchers("/customer/**").hasRole("CUSTOMER");
            registry.requestMatchers("/cart/**").hasRole("CUSTOMER");
            registry.requestMatchers("/signup").permitAll();
            registry.requestMatchers("/update-password").authenticated();
            registry.requestMatchers("/registered-emails").authenticated();
            registry.requestMatchers("/registered-phones").authenticated();
            registry.anyRequest().authenticated();
        })
        .formLogin(form -> form.loginPage("/login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/welcome").permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll())
        .build();
    }


    @Bean
    public  UserDetailsService userDetailsService() {
        return userDetailService;
    }

    @Bean
    public  AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public  PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
