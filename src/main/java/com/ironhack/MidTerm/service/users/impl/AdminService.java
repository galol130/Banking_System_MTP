package com.ironhack.MidTerm.service.users.impl;

import com.ironhack.MidTerm.enums.RoleName;
import com.ironhack.MidTerm.model.users.Admin;
import com.ironhack.MidTerm.model.users.Role;
import com.ironhack.MidTerm.repository.AdminRepository;
import com.ironhack.MidTerm.repository.RoleRepository;
import com.ironhack.MidTerm.service.users.interfaces.IAdminService;
import com.ironhack.MidTerm.utils.PasswordUtil;
import com.ironhack.MidTerm.utils.styles.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void createAdminIfNecessary() {
        List<Admin> adminList = adminRepository.findAll();
        if(adminList.size() < 1){
            Admin admin = adminRepository.save(new Admin("admin", PasswordUtil.encryptPassword("123456"), "Admin firstName", "Admin lastName", "Admin PersonalId"));
            roleRepository.save(new Role("ADMIN", admin));
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\tAdmin created!!");
        }else{
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\tAdmin already exists in Database");
        }
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT);
    }
}