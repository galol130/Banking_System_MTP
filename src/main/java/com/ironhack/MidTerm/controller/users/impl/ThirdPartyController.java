package com.ironhack.MidTerm.controller.users.impl;

import com.ironhack.MidTerm.controller.users.interfaces.IThirdPartyController;
import com.ironhack.MidTerm.repository.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThirdPartyController implements IThirdPartyController {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;




}
