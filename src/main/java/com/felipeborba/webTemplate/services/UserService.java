package com.felipeborba.webTemplate.services;

import com.felipeborba.webTemplate.dto.*;
import com.felipeborba.webTemplate.entities.Role;
import com.felipeborba.webTemplate.entities.User;
import com.felipeborba.webTemplate.repositories.RoleRepository;
import com.felipeborba.webTemplate.repositories.UserRepository;
import com.felipeborba.webTemplate.services.exceptions.DatabaseException;
import com.felipeborba.webTemplate.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtEncoder jwtEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageRequest) {
        Page<User> list = this.userRepository.findAll(pageRequest);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity no found"));
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User user = new User();
        copyDtoToEntity(dto, user);
        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        user = this.userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        try {
            User user = this.userRepository.getReferenceById(id);
            copyDtoToEntity(dto, user);
            user = this.userRepository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("Email or password incorrect");
        }
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

    public void delete(Long id) {
        try {
            this.userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity vaiolation");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        user.getRoles().clear();
        for (RoleDTO roleDTO : dto.getRoles()) {
            Role role = this.roleRepository.getReferenceById(roleDTO.getId());
            user.getRoles().add(role);
        }
    }
}
