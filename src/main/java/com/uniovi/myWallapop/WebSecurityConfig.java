package com.uniovi.myWallapop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/script/**", "/", "/signup", "/login/**").permitAll()
                //.antMatchers("/fragments/add").hasAuthority("ROLE_PROFESSOR")
                //.antMatchers("/mark/edit/*").hasAuthority("ROLE_PROFESSOR")
                //.antMatchers("/mark/delete/*").hasAuthority("ROLE_PROFESSOR")
                //.antMatchers("/mark/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN")
                //.antMatchers("/user/**").hasAnyRole("ADMIN")
                //.antMatchers("/professor/add").hasAnyRole("ADMIN")
                //.antMatchers("/professor/delete/*").hasAnyRole("ADMIN")
                //.antMatchers("/professor/details/*").hasAnyRole("ADMIN", "PROFESSOR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll().defaultSuccessUrl("/home")
                .failureUrl("/login-error")
                .and()
                .logout().permitAll();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
