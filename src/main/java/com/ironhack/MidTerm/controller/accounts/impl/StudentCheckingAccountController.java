package com.ironhack.MidTerm.controller.accounts.impl;

import com.ironhack.MidTerm.controller.accounts.interfaces.IStudentCheckingAccountController;
import com.ironhack.MidTerm.model.accounts.StudentCheckingAccount;
import com.ironhack.MidTerm.repository.StudentCheckingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentCheckingAccountController implements IStudentCheckingAccountController {
    @Autowired
    private StudentCheckingAccountRepository studentCheckingAccountRepository;


    @GetMapping(value = "/student-checking-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCheckingAccount> getStudentCheckingAccounts() {
        return studentCheckingAccountRepository.findAll();
    }


}
