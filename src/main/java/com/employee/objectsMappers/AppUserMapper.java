package com.employee.objectsMappers;

import com.employee.dto.AppUserDTO;
import com.employee.model.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper implements Mapper<AppUser, AppUserDTO> {

    @Override
    public AppUserDTO mapToDTO(AppUser entity) {
        if (entity == null) {
            return null;
        }

        return AppUserDTO.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .roles(entity.getRoles())
                .build();
    }

    @Override
    public AppUser mapFromDTO(AppUserDTO dto) {
        if (dto == null) {
            return null;
        }

        return AppUser.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .roles(dto.getRoles())
                .build();
    }
}
