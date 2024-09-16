package com.felipeborba.webTemplate.config;

import com.felipeborba.webTemplate.dto.UserInsertDTO;
import com.felipeborba.webTemplate.entity.UserRole;
import com.felipeborba.webTemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig implements CommandLineRunner {
    @Value("${api.admin.login}")
    private String adminLogin;
    @Value("${api.admin.password}")
    private String adminPassword;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        var user = new UserInsertDTO();
        user.setLogin(adminLogin);
        user.setPassword(adminPassword);
        user.setRole(UserRole.ADMIN);
        this.userService.insert(user);
    }
}
