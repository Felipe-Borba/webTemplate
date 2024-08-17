package com.felipeborba.webTemplate.security;

import com.felipeborba.webTemplate.security.dto.LoginRequestDto;
import com.felipeborba.webTemplate.security.dto.LoginResponseDto;
import com.felipeborba.webTemplate.security.dto.RegisterRequestDto;
import com.felipeborba.webTemplate.user.entity.User;
import com.felipeborba.webTemplate.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto data) {
        LoginResponseDto response = this.authorizationService.login(data);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDto data) {
        this.userService.createUser(data.login(), data.password(), data.role());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        var currentUser = this.authorizationService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }
}
