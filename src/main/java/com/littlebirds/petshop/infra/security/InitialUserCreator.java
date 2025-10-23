package com.littlebirds.petshop.infra.security;


import com.littlebirds.petshop.application.services.UserService;
import com.littlebirds.petshop.domain.dtos.user.AdminRegisterDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitialUserCreator {

    @Value("${api.security.user}")
    private String username;
    @Value("${api.security.password}")
    private String password;

    private UserService userService;

    public InitialUserCreator(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createInitialUser() {
        if (!userService.existUser(username)) {
            AdminRegisterDto register = new AdminRegisterDto(
                    "admin",
                    username,
                    password,
                    "0000000000"
            );
            userService.createUserAdmin(register);
        }

    }
}
