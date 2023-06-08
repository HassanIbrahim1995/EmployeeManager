package com.employee.controller;

import com.employee.dto.RoleDTO;
import com.employee.exception.RoleException;
import com.employee.model.Role;
import com.employee.objectsMappers.Mapper;
import com.employee.service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl roleService;
    private final Mapper<Role, RoleDTO> roleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.getById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            RoleDTO roleDTO = roleMapper.mapToDTO(role);
            return ResponseEntity.ok(roleDTO);
        } else {
            throw new RoleException("Person with id: " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        Role role = roleMapper.mapFromDTO(roleDTO);
        Optional<Role> savedRoleOptional = roleService.save(role);
        if (savedRoleOptional.isPresent()) {
            Role savedRole = savedRoleOptional.get();
            RoleDTO savedRoleDTO = roleMapper.mapToDTO(savedRole);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRoleDTO);
        } else {
            throw new RoleException("Person with name: " + roleDTO.getName() + " already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        Optional<Role> existingRoleOptional = roleService.getById(id);
        if (existingRoleOptional.isPresent()) {
            Role existingRole = existingRoleOptional.get();
            Role updatedRole = roleMapper.mapFromDTO(roleDTO);
            updatedRole.setId(existingRole.getId());
            Optional<Role> savedRoleOptional = roleService.update(updatedRole);
            if (savedRoleOptional.isPresent()) {
                Role savedRole = savedRoleOptional.get();
                RoleDTO savedRoleDTO = roleMapper.mapToDTO(savedRole);
                return ResponseEntity.ok(savedRoleDTO);
            }
        }
        throw new RoleException("Failed to update role with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        List<RoleDTO> roleDTOs = roles.stream()
                .map(roleMapper::mapToDTO)
                .toList();
        return ResponseEntity.ok(roleDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.getById(id);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            roleService.delete(role);
            return ResponseEntity.noContent().build();
        } else {
            throw new RoleException("Role with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
