package com.ironhack.MidTerm.security;

import com.ironhack.MidTerm.service.users.impl.CustomUserDetailsService;
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
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/account-holder").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/third-party").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/checking-account", "/student-checking-account", "/savings-account").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/account-holder/{id}/primary-address", "/account-holder/{id}/secondary-address").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/account/{id}/balance", "/account/{id}/status").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/account/{id}/basic-details").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/checking-accounts", "/student-checking-accounts", "/savings-accounts").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/accounts/balance", "/account/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/transactions/destination-account/{id}", "/transactions/origin-account/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/transactions/account-holder/{account-holder-id}").authenticated()
                .antMatchers(HttpMethod.GET, "/transactions/third-party/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/transaction/transfer-from-account/{account_id}").permitAll()
                .antMatchers(HttpMethod.POST, "/transaction/deposit", "/transaction/collect").permitAll()
                .anyRequest().permitAll();
    }
}
