package com.felipeborba.webTemplate.user.controller;

import com.felipeborba.webTemplate.user.dto.CreateUserRequestDto;
import com.felipeborba.webTemplate.user.entity.User;
import com.felipeborba.webTemplate.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/client")
    public ResponseEntity<?> client() {
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid CreateUserRequestDto request) {
        User user = this.userService.createUser(request.login(), request.password(), request.role());
        return ResponseEntity.ok(user);
    }
}
