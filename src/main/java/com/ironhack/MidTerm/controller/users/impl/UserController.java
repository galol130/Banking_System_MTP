package com.ironhack.MidTerm.controller.users.impl;

import com.ironhack.MidTerm.controller.users.interfaces.IUserController;
import com.ironhack.MidTerm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController {
    @Autowired
    private UserRepository userRepository;


}
