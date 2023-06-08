package com.employee.controller;

import com.employee.dto.RoleDTO;
import com.employee.model.Role;
import com.employee.objectsMappers.Mapper;
import com.employee.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final GenericService<Role> roleService;
    private final Mapper<Role, RoleDTO> roleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        RoleDTO roleDTO = roleMapper.mapToDTO(role);
        return ResponseEntity.ok(roleDTO);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
        Role role = roleMapper.mapFromDTO(roleDTO);
        Role savedRole = roleService.save(role);
        RoleDTO savedRoleDTO = roleMapper.mapToDTO(savedRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoleDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        Role existingRole = roleService.getById(id);
        Role updatedRole = roleMapper.mapFromDTO(roleDTO);
        updatedRole.setId(existingRole.getId());
        Role savedRole = roleService.update(updatedRole);
        RoleDTO savedRoleDTO = roleMapper.mapToDTO(savedRole);
        return ResponseEntity.ok(savedRoleDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        Role role = roleService.getById(id);
        roleService.delete(role);
        return ResponseEntity.noContent().build();
    }
}

