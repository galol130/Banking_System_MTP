package com.ironhack.MidTerm.service.users.impl;

import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyGetRequestDTO;
import com.ironhack.MidTerm.model.users.ThirdParty;
import com.ironhack.MidTerm.repository.ThirdPartyRepository;
import com.ironhack.MidTerm.service.users.interfaces.IThirdPartyService;
import com.ironhack.MidTerm.utils.EncryptorUtil;
import com.ironhack.MidTerm.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ThirdPartyService implements IThirdPartyService {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;


    @Override
    public ThirdPartyGetRequestDTO createThirdParty(ThirdPartyCreationRequestDTO creationRequestDTO) {
        try {
            ThirdParty thirdParty = thirdPartyRepository.save(
                    new ThirdParty(creationRequestDTO.getName(),
                            PasswordUtil.encryptPassword(creationRequestDTO.getHashedKey())
                    )
            );
            return convertThirdPartyToDTO(thirdParty);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Body data does not match 'ThirdPartyCreationRequestDTO' ");
        }
    }

    public ThirdPartyGetRequestDTO convertThirdPartyToDTO(ThirdParty thirdParty) {
        return new ThirdPartyGetRequestDTO(thirdParty.getId(), thirdParty.getName());
    }
}
