package com.employee.controller;

import com.employee.ResponseMessage.ResponseMessage;
import com.employee.dto.AppUserDTO;
import com.employee.model.AppUser;
import com.employee.objectsMappers.AppUserMapper;
import com.employee.service.AppUserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final AppUserServiceImpl appUserService;
    private final AppUserMapper appUserMapper;

    public UserController(AppUserServiceImpl appUserService, AppUserMapper appUserMapper) {
        this.appUserService = appUserService;
        this.appUserMapper = appUserMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getUserById(@PathVariable Long id) {
        Optional<AppUser> userOptional = appUserService.getById(id);
        if (userOptional.isPresent()) {
            AppUserDTO userDTO = appUserMapper.mapToDTO(userOptional.get());
            return ResponseEntity.ok(userDTO);
        } else {
            throw new UsernameNotFoundException("Person with ID " + id + " not found.");
        }
    }

    @GetMapping
    public ResponseEntity<List<AppUserDTO>> getAllUsers() {
        List<AppUser> users = appUserService.getAll();
        List<AppUserDTO> userDTOs = users.stream()
                .map(appUserMapper::mapToDTO)
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping
    public ResponseEntity<AppUserDTO> createUser(@RequestBody AppUserDTO userDTO) {
        AppUser user = appUserMapper.mapFromDTO(userDTO);
        Optional<AppUser> createdUserOptional = appUserService.save(user);

        if (createdUserOptional.isPresent()) {
            AppUser createdUser = createdUserOptional.get();
            AppUserDTO createdUserDTO = appUserMapper.mapToDTO(createdUser);
            return ResponseEntity.ok(createdUserDTO);
        } else {
            throw new UsernameNotFoundException("Failed to create user.");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<AppUserDTO> updateUser(@PathVariable Long id, @RequestBody AppUserDTO userDTO) {
        AppUser existingUser = appUserService.getById(id)
                .orElseThrow(() ->
                new UsernameNotFoundException("User with ID " + id + " not found.")
                );
        AppUser updatedUser = appUserMapper.mapFromDTO(userDTO);
        updatedUser.setId(existingUser.getId()); // Ensure the ID remains the same
        Optional<AppUser> savedUser = appUserService.update(updatedUser);
        AppUserDTO savedUserDTO = appUserMapper.mapToDTO(savedUser.get());
        return ResponseEntity.ok(savedUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable Long id) {
        Optional<AppUser> User =  appUserService.getById(id);
        if (User.isPresent()) {
            appUserService.delete(User.get());
            return ResponseEntity.ok(new ResponseMessage("Person with name " + User.get().getUsername() + " was deleted."));
        }else {
            throw new UsernameNotFoundException("Failed to create user.");
        }
    }
}

