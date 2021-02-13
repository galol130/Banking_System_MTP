package com.ironhack.MidTerm.service.users.impl;

import com.ironhack.MidTerm.controller.users.DTO.AccountHolderCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.AccountHolderGetRequestDTO;
import com.ironhack.MidTerm.enums.RoleName;
import com.ironhack.MidTerm.model.Address;
import com.ironhack.MidTerm.model.users.AccountHolder;
import com.ironhack.MidTerm.model.users.Role;
import com.ironhack.MidTerm.model.users.User;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.repository.RoleRepository;
import com.ironhack.MidTerm.repository.UserRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAccountHolderService;
import com.ironhack.MidTerm.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService implements IAccountHolderService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    public Optional<AccountHolder> findAccountHolderById(Long id) {
        return accountHolderRepository.findById(id);
    }

    @Override
    public AccountHolder getAccountHolderByUsername(String username) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findByUsername(username);
        if (accountHolder.isPresent()) {
            return accountHolder.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has no accounts associated");
        }
    }

    @Override
//  The account holder created will have the default role "STANDARD_USER"
//  The account holder created will have the same address for principal and secondary fields
    public AccountHolderGetRequestDTO createAccountHolder(AccountHolderCreationRequestDTO creationRequestDTO) {
        AccountHolder accountHolder;

        //Check if username is not already taken
        Optional<User> user = userRepository.findByUsername(creationRequestDTO.getUsername());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        //Check if the Personal ID is not already in the DB
        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findByPersonalId(creationRequestDTO.getPersonalId());
        if(accountHolderOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already exists an account holder with that personal ID");
        }

        //Create new Address, used for primary and secondary addresses
        Address address = new Address(
                creationRequestDTO.getStreet(),
                creationRequestDTO.getDoorNumber(),
                creationRequestDTO.getAptNumber(),
                creationRequestDTO.getExtraInfo(),
                creationRequestDTO.getCity(),
                creationRequestDTO.getStateOrProvince(),
                creationRequestDTO.getPostalCode(),
                creationRequestDTO.getCountry());
        try {
            accountHolder = accountHolderRepository.save(
                    new AccountHolder(
                            creationRequestDTO.getUsername(),
                            PasswordUtil.encryptPassword(creationRequestDTO.getPassword()),
                            creationRequestDTO.getFirstName(),
                            creationRequestDTO.getLastName(),
                            creationRequestDTO.getPersonalId(),
                            creationRequestDTO.getDateOfBirth(),
                            address,
                            address)
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data provided doesn't match 'AccountHolderCreationRequestDTO' ");
        }
        roleRepository.save(new Role("STANDARD_USER", accountHolder));
        return convertAccountHolderToDTO(accountHolder);

    }

    public AccountHolderGetRequestDTO convertAccountHolderToDTO(AccountHolder accountHolder) {
        return new AccountHolderGetRequestDTO(
                accountHolder.getUsername(),
                accountHolder.getFirstName(),
                accountHolder.getLastName(),
                accountHolder.getDateOfBirth(),
                accountHolder.getPrimaryAddress().getStreet(),
                accountHolder.getPrimaryAddress().getDoorNumber(),
                accountHolder.getPrimaryAddress().getAptNumber(),
                accountHolder.getPrimaryAddress().getExtraInfo(),
                accountHolder.getPrimaryAddress().getCity(),
                accountHolder.getPrimaryAddress().getStateOrProvince(),
                accountHolder.getPrimaryAddress().getPostalCode(),
                accountHolder.getPrimaryAddress().getCountry()
        );
    }

    @Override
    public List<Address> changePrimaryAddress(Long id, Address address) {

        //First check if the Account Holder exist
        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findById(id);
        if(accountHolderOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID does not match any Account Holder");
        AccountHolder accountHolder = accountHolderOptional.get();

        accountHolder.setPrimaryAddress(address);
        accountHolderRepository.save(accountHolder);

        return List.of(accountHolder.getPrimaryAddress(),accountHolder.getSecondaryAddress());
    }

    @Override
    public List<Address> changeSecondaryAddress(Long id, Address address) {

        //First check if the Account Holder exist
        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findById(id);
        if(accountHolderOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID does not match any Account Holder");
        AccountHolder accountHolder = accountHolderOptional.get();

        accountHolder.setSecondaryAddress(address);
        accountHolderRepository.save(accountHolder);

        return List.of(accountHolder.getPrimaryAddress(),accountHolder.getSecondaryAddress());
    }

}
