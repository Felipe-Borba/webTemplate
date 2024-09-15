package com.felipeborba.webTemplate.controller;

import com.felipeborba.webTemplate.service.AuthenticationService;
import com.felipeborba.webTemplate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

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

    @PostMapping("/token")
    public ResponseEntity<String> authenticate(Authentication authentication) {
        var response = authenticationService.authenticate(authentication);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/")
    public ResponseEntity<String> hello(Authentication authentication) {
        return ResponseEntity.ok().body("Hello, " + authentication.getName() + "!");
    }
}
