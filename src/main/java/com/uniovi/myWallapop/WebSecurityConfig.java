package com.uniovi.myWallapop;

import com.uniovi.myWallapop.entities.Log;
import com.uniovi.myWallapop.services.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private LogsService logger;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/signup", "/login-error").not().authenticated()
                .antMatchers("/css/**", "/images/**", "/script/**", "/").permitAll()
                .antMatchers("/user/userslist").hasAuthority("ROLE_ADMIN")
                .antMatchers("/offer/**").hasAuthority("ROLE_STANDARD")
                .antMatchers("/user/logs**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll().defaultSuccessUrl("/default")
                .failureUrl("/login-error")
                .and()
                .logout().addLogoutHandler((request, response, authentication) ->
                        {
                            logger.addLog(new Log(Log.Tipo.LOGOUT, "Usuario ha cerrado sesión"));
                        }
                ).permitAll();
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
