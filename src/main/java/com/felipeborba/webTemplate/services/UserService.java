package com.felipeborba.webTemplate.services;

import com.felipeborba.webTemplate.dto.LoginRequestDTO;
import com.felipeborba.webTemplate.dto.LoginResponseDTO;
import com.felipeborba.webTemplate.dto.UserDTO;
import com.felipeborba.webTemplate.entities.Role;
import com.felipeborba.webTemplate.entities.User;
import com.felipeborba.webTemplate.repositories.UserRepository;
import com.felipeborba.webTemplate.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository repository;
	@Autowired
	private AuthService authService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private JwtEncoder jwtEncoder;

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		authService.validateSelfOrAdmin(id);
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new UserDTO(entity);
	}

	@Transactional(readOnly = true)
	public LoginResponseDTO login(LoginRequestDTO dto) {
		User user = repository.findByEmail(dto.getEmail());
		if (user == null) {
			logger.error("User not found: " + dto.getEmail());
			throw new ResourceNotFoundException("Email or password incorrect");
		}
		logger.info("User found: " + user.getId());
		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			throw new ResourceNotFoundException("Email or password incorrect");
		}

		Instant now = Instant.now();
		long expiresIn = 86400L;

		String scopes = user.getRoles()
				.stream()
				.map(Role::getAuthority)
				.collect(Collectors.joining(" "));

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("mybackend")
				.subject(user.getId().toString())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiresIn))
				.claim("scope", scopes)
				.build();

		String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		return new LoginResponseDTO(jwtValue, expiresIn);
	}
}
