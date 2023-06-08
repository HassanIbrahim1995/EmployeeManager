package com.employee.service;

import com.employee.model.Role;
import com.employee.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements GenericService<Role> {

    private final RoleRepository roleRepository;

    public Optional<Role> getByName(String name) {
        return Optional.ofNullable(roleRepository.findByName(name));
    }

    @Override
    public Optional<Role> getById(Long id) {
        log.info("Getting Role by id: {}", id);
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> save(Role entity) {
        log.info("Saving Role: {}", entity);
        Role savedRole = roleRepository.save(entity);
        return Optional.of(savedRole);
    }

    @Override
    public void delete(Role entity) {
        log.info("Deleting Role: {}", entity);
        if (roleRepository.existsById(entity.getId())) {
            roleRepository.delete(entity);
        }
    }

    @Override
    public Optional<Role> update(Role entity) {
        log.info("Updating Role: {}", entity);
        if (roleRepository.existsById(entity.getId())) {
                Role updatedRole = roleRepository.save(entity);
                return Optional.of(updatedRole);
        }
        return Optional.empty();
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
