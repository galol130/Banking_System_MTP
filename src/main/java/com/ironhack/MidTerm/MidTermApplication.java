package com.ironhack.MidTerm;

import com.ironhack.MidTerm.service.users.interfaces.IAdminService;
import com.ironhack.MidTerm.utils.styles.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MidTermApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MidTermApplication.class, args);
	}

	@Autowired
    private IAdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\n\tUp and Running!!!");
        System.out.println(ConsoleColors.WHITE_BRIGHT);

        adminService.createAdminIfNecessary();
    }

}
