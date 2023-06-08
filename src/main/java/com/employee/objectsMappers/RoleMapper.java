package com.employee.objectsMappers;

import com.employee.dto.AppUserDTO;
import com.employee.dto.RoleDTO;
import com.employee.model.AppUser;
import com.employee.model.Role;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper implements Mapper<Role, RoleDTO> {

    private final Mapper<AppUser, AppUserDTO> appUserMapper;

    public RoleMapper(Mapper<AppUser, AppUserDTO> appUserMapper) {
        this.appUserMapper = appUserMapper;
    }

    @Override
    public RoleDTO mapToDTO(Role entity) {
        if (entity == null) {
            return null;
        }

        return RoleDTO.builder()
                .name(entity.getName())
                .users(mapUsersToDTO(entity.getUsers()))
                .build();
    }

    @Override
    public Role mapFromDTO(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        return Role.builder()
                .name(dto.getName())
                .users(mapUsersFromDTO(dto.getUsers()))
                .build();
    }

    private Set<AppUserDTO> mapUsersToDTO(Set<AppUser> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(appUserMapper::mapToDTO)
                .collect(Collectors.toSet());
    }

    private Set<AppUser> mapUsersFromDTO(Set<AppUserDTO> usersDTO) {
        if (usersDTO == null) {
            return null;
        }

        return usersDTO.stream()
                .map(appUserMapper::mapFromDTO)
                .collect(Collectors.toSet());
    }
}

