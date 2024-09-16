package com.felipeborba.webTemplate.user.controller;

import com.felipeborba.webTemplate.user.dto.UserDTO;
import com.felipeborba.webTemplate.user.dto.UserInsertDTO;
import com.felipeborba.webTemplate.user.dto.UserUpdateDTO;
import com.felipeborba.webTemplate.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        // PARAMETROS: page, size, sort
        return ResponseEntity.ok().body(this.userService.findAllPaged(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(this.userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody @Valid UserInsertDTO dto) {
        UserDTO newDto = this.userService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id, @RequestBody @Valid UserUpdateDTO dto) {
        UserDTO newDto = this.userService.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable String id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
