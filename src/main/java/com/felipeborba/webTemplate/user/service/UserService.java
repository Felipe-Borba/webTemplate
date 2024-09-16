package com.felipeborba.webTemplate.user.service;

import com.felipeborba.webTemplate.security.service.AuthenticationService;
import com.felipeborba.webTemplate.user.dto.UserDTO;
import com.felipeborba.webTemplate.user.dto.UserInsertDTO;
import com.felipeborba.webTemplate.user.dto.UserUpdateDTO;
import com.felipeborba.webTemplate.user.entity.User;
import com.felipeborba.webTemplate.user.repository.UserRepository;
import com.felipeborba.webTemplate.exception.DatabaseException;
import com.felipeborba.webTemplate.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authService;

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User user = new User();
        copyDtoToEntity(dto, user);
        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));

        user = this.userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(String id,UserUpdateDTO dto) {
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
    public Page<UserDTO> findAllPaged(Pageable pageRequest) {
        Page<User> list = this.userRepository.findAll(pageRequest);
        return list.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(String id) {
        authService.validateSelfOrAdmin(id);
        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity no found"));
        return new UserDTO(user);
    }

    public void delete(String id) {
        try {
            this.userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity vaiolation");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User user) {
//        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setRole(dto.getRole());
    }
}
