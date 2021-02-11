package com.ironhack.MidTerm.service.users.impl;

import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.model.users.Admin;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.repository.AdminRepository;
import com.ironhack.MidTerm.security.CustomUserDetails;
import com.ironhack.MidTerm.service.users.interfaces.ICustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements ICustomUserDetailsService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails customUserDetails;
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername(username);
        if (!accountHolder.isPresent()) {
            Optional<Admin> admin = adminRepository.findByUsername(username);
            if (!admin.isPresent()) {
                throw new UsernameNotFoundException("User does not exist");
            } else {
                customUserDetails = new CustomUserDetails(admin.get());
            }
        } else {
            customUserDetails = new CustomUserDetails(accountHolder.get());
        }

        return customUserDetails;
    }
}
