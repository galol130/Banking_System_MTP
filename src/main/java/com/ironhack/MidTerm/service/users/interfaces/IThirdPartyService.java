package com.ironhack.MidTerm.service.users.interfaces;

import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyGetRequestDTO;
import com.ironhack.MidTerm.model.users.ThirdParty;

import java.util.List;

public interface IThirdPartyService {
    ThirdPartyGetRequestDTO createThirdParty(ThirdPartyCreationRequestDTO creationRequestDTO);

    List<ThirdPartyGetRequestDTO> getAllThirdParties();

    ThirdPartyGetRequestDTO convertThirdPartyToDTO(ThirdParty thirdParty);
}
