package com.ironhack.MidTerm.security;

import com.ironhack.MidTerm.service.users.interfaces.ICustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable().authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/accounts").hasRole("ADMIN")
//                .mvcMatchers(HttpMethod.POST, "/author").hasRole("ADMIN")
//                .mvcMatchers(HttpMethod.PUT, "/author/{id}").hasAnyRole("ADMIN", "CONTRIBUTOR")
//                .mvcMatchers(HttpMethod.DELETE, "/author/{id}").hasRole("ADMIN")
//                .mvcMatchers(HttpMethod.POST, "/post").hasAnyRole("ADMIN", "CONTRIBUTOR")
//                .mvcMatchers(HttpMethod.PUT, "/post/{id}").hasAnyRole("ADMIN", "CONTRIBUTOR")
//                .mvcMatchers(HttpMethod.DELETE, "/post/{id}").hasRole("ADMIN")
                .anyRequest().permitAll();
    }

}
