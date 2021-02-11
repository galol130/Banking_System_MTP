package com.ironhack.MidTerm.service.users.impl;

import com.ironhack.MidTerm.model.users.Role;
import com.ironhack.MidTerm.model.users.User;
import com.ironhack.MidTerm.repository.UserRepository;
import com.ironhack.MidTerm.security.CustomUserDetails;
import com.ironhack.MidTerm.service.users.interfaces.ICustomUserDetailsService;
import com.ironhack.MidTerm.utils.styles.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements ICustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("Wrong username");
        }else{
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT+ "\n\t" + user.get().getUsername());

            for (Role role:user.get().getRoles() ) {
                System.out.println(role.getRoleName());
            }
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT);
            return new CustomUserDetails(user.get());
        }
    }
}
