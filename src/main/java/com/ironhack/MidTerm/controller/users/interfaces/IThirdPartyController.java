package com.ironhack.MidTerm.controller.users.interfaces;

import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyGetRequestDTO;
import com.ironhack.MidTerm.model.users.ThirdParty;

public interface IThirdPartyController {
    ThirdParty getThirdPartyById(Long id);

    ThirdPartyGetRequestDTO createThirdParty(ThirdPartyCreationRequestDTO creationRequestDTO);
}
