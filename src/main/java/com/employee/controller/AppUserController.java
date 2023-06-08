package com.employee.controller;

import com.employee.dto.AppUserDTO;
import com.employee.model.AppUser;
import com.employee.objectsMappers.Mapper;
import com.employee.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app-users")
@RequiredArgsConstructor
public class AppUserController {

    private final GenericService<AppUser> appUserService;
    private final Mapper<AppUser, AppUserDTO> appUserMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getAppUserById(@PathVariable Long id) {
        AppUser appUser = appUserService.getById(id);
        AppUserDTO appUserDTO = appUserMapper.mapToDTO(appUser);
        return ResponseEntity.ok(appUserDTO);
    }

    @PostMapping
    public ResponseEntity<AppUserDTO> createAppUser(@RequestBody AppUserDTO appUserDTO) {
        AppUser appUser = appUserMapper.mapFromDTO(appUserDTO);
        AppUser savedAppUser = appUserService.save(appUser);
        AppUserDTO savedAppUserDTO = appUserMapper.mapToDTO(savedAppUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppUserDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserDTO> updateAppUser(@PathVariable Long id, @RequestBody AppUserDTO appUserDTO) {
        AppUser existingAppUser = appUserService.getById(id);
        AppUser updatedAppUser = appUserMapper.mapFromDTO(appUserDTO);
        updatedAppUser.setId(existingAppUser.getId());
        AppUser savedAppUser = appUserService.update(updatedAppUser);
        AppUserDTO savedAppUserDTO = appUserMapper.mapToDTO(savedAppUser);
        return ResponseEntity.ok(savedAppUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppUser(@PathVariable Long id) {
        AppUser appUser = appUserService.getById(id);
        appUserService.delete(appUser);
        return ResponseEntity.noContent().build();
    }
}

