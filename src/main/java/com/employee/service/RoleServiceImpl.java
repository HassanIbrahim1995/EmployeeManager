package com.employee.service;

import com.employee.exception.RoleException;
import com.employee.model.Role;
import com.employee.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements GenericService<Role> {

    private final RoleRepository roleRepository;

    public Role getByName(String name) {
        return Optional.ofNullable(roleRepository.findByName(name))
                .orElseThrow(() -> new RoleException("Role with name " + name + " not found."));
    }

    @Override
    public Role getById(Long id) {
        log.info("Getting Role by id: {}", id);
        return roleRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Role with id {} not found", id);
                    return new RoleException("Role with id " + id + " not found.");
                });
    }

    @Override
    public Role save(Role entity) {
        log.info("Saving Role: {}", entity);
        try {
            return roleRepository.save(entity);
        } catch (DataAccessException ex) {
            log.error("Unable to save Role: {}", entity, ex);
            throw new RoleException(String.format("Unable to save role %s", entity), ex);
        }
    }


    @Override
    public void delete(Role entity) {
        log.info("Deleting Role: {}", entity);
        if (!roleRepository.existsById(entity.getId())) {
            log.error("Role with id {} not found, unable to delete", entity.getId());
            throw new RoleException("Role with id " + entity.getId() + " not found, unable to delete.");
        }
        roleRepository.delete(entity);
    }

    @Override
    public Role update(Role entity) {
        log.info("Updating Role: {}", entity);
        if (!roleRepository.existsById(entity.getId())) {
            log.error("Role with id {} not found, unable to update", entity.getId());
            throw new RoleException("Role with id " + entity.getId() + " not found, unable to update.");
        }
        try {
            return roleRepository.save(entity);
        } catch (DataAccessException ex) {
            log.error("Unable to update Role: {}", entity, ex);
            throw new RoleException("Unable to update role " + entity, ex);
        }
    }

}

