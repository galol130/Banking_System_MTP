package com.ironhack.MidTerm.service.users.interfaces;

import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyGetRequestDTO;

public interface IThirdPartyService {
    ThirdPartyGetRequestDTO createThirdParty(ThirdPartyCreationRequestDTO creationRequestDTO);
}
