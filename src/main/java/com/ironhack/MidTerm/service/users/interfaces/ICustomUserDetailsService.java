package com.ironhack.MidTerm.service.users.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ICustomUserDetailsService extends UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
