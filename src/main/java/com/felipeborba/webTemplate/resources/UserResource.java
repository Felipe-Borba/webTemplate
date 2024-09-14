package com.felipeborba.webTemplate.resources;

import com.felipeborba.webTemplate.dto.LoginRequestDTO;
import com.felipeborba.webTemplate.dto.LoginResponseDTO;
import com.felipeborba.webTemplate.dto.UserDTO;
import com.felipeborba.webTemplate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
		LoginResponseDTO response = service.login(dto);
		return ResponseEntity.ok().body(response);
	}
}
