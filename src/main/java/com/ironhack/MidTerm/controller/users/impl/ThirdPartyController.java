package com.ironhack.MidTerm.controller.users.impl;

import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyGetRequestDTO;
import com.ironhack.MidTerm.controller.users.interfaces.IThirdPartyController;
import com.ironhack.MidTerm.model.users.ThirdParty;
import com.ironhack.MidTerm.repository.ThirdPartyRepository;
import com.ironhack.MidTerm.service.users.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ThirdPartyController implements IThirdPartyController {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private IThirdPartyService thirdPartyService;


    @GetMapping(value = "/third-party/{id}")
    public ThirdParty getThirdPartyById(Long id) {
        return null;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/third-party")
    public List<ThirdPartyGetRequestDTO> getAllThirdParties(){
        return thirdPartyService.getAllThirdParties();

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdPartyGetRequestDTO createThirdParty(@RequestBody @Valid ThirdPartyCreationRequestDTO creationRequestDTO) {
        return thirdPartyService.createThirdParty(creationRequestDTO);
    }
}
