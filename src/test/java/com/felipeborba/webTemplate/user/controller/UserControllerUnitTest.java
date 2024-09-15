package com.felipeborba.webTemplate.user.controller;

import com.felipeborba.webTemplate.controller.UserController;
import com.felipeborba.webTemplate.user.dto.CreateUserRequestDto;
import com.felipeborba.webTemplate.entity.User;
import com.felipeborba.webTemplate.entity.UserRole;
import com.felipeborba.webTemplate.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerUnitTest {

    @Test
    void createUserShouldReturnCreatedUser() {
        UserService userService = Mockito.mock(UserService.class);
        User user = new User();
        when(userService.createUser(any(), any(), any())).thenReturn(user);
        UserController userController = new UserController(userService);

        var response = userController.create(new CreateUserRequestDto("", "", UserRole.ADMIN));

        assertEquals(ResponseEntity.ok(user), response);
    }

}
