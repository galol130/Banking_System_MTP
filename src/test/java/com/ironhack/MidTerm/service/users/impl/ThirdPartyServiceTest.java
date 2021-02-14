package com.ironhack.MidTerm.service.users.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyCreationRequestDTO;
import com.ironhack.MidTerm.controller.users.DTO.ThirdPartyGetRequestDTO;
import com.ironhack.MidTerm.model.users.ThirdParty;
import com.ironhack.MidTerm.repository.AccountHolderRepository;
import com.ironhack.MidTerm.repository.AccountRepository;
import com.ironhack.MidTerm.repository.ThirdPartyRepository;
import com.ironhack.MidTerm.utils.PasswordUtil;
import org.hibernate.validator.constraints.Length;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotEmpty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ThirdPartyServiceTest {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private ThirdPartyService thirdPartyService;


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        ThirdPartyCreationRequestDTO requestDTO = new ThirdPartyCreationRequestDTO(
                "First Third Party",
                "hashedkey");
        ThirdParty thirdParty = thirdPartyRepository.save(
                new ThirdParty(requestDTO.getName(), PasswordUtil.encryptPassword(requestDTO.getHashedKey())));
    }

    @AfterEach
    void tearDown() {
        thirdPartyRepository.deleteAll();
    }

    @Test
    void createThirdParty_correctDTO_ThirdPartyGetRequestDTO() {
        ThirdPartyCreationRequestDTO requestDTO = new ThirdPartyCreationRequestDTO(
                "Second Third Party",
                "hashedkey");
        ThirdPartyGetRequestDTO thirdPartyDTO = thirdPartyService.createThirdParty(requestDTO);

        assertEquals("Second Third Party", thirdPartyDTO.getName());
    }

    @Test
    void createThirdParty_wrongDTO_exception() {
        ThirdPartyCreationRequestDTO requestDTO = new ThirdPartyCreationRequestDTO(
                "",
                "");

        //DTO can't be created with empty strings
    }

    @Test
    void getAllThirdParties_ok_ListThirdPartyGetRequestDTO(){
        List<ThirdPartyGetRequestDTO> thirdPartyList = thirdPartyService.getAllThirdParties();
        assertEquals(1, thirdPartyList.size());
    }

    @Test
    void getAllThirdParties_empty_exception(){
        thirdPartyRepository.deleteAll();
        assertThrows(ResponseStatusException.class, ()-> thirdPartyService.getAllThirdParties());
    }

    @Test
    void convertThirdPartyToDTO() {
//  Nothing to test here
    }
}