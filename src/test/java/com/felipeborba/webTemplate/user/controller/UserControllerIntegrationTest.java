package com.felipeborba.webTemplate.user.controller;

import com.felipeborba.webTemplate.security.AuthorizationService;
import com.felipeborba.webTemplate.security.TokenService;
import com.felipeborba.webTemplate.entity.User;
import com.felipeborba.webTemplate.repository.UserRepository;
import com.felipeborba.webTemplate.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerIntegrationTest {
    // test types Unit < Integration < Acceptance

    @Autowired
    private MockMvc mcv;

    @MockBean
    private UserService userService;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void createUserShouldReturnCreatedUser() throws Exception {
        when(userService.createUser(any(), any(), any())).thenReturn(new User());

        mcv.perform(post("/"))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
