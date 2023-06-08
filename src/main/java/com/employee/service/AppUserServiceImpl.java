package com.employee.service;

import com.employee.model.AppUser;
import com.employee.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements UserDetailsService, GenericService<AppUser> {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<AppUser> getById(Long id) {
        log.info("Getting AppUser by id: {}", id);
        return appUserRepository.findById(id);
    }

    @Override
    public Optional<AppUser> save(AppUser entity) {
        log.info("Saving AppUser: {}", entity);
        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);
        try {
            AppUser savedUser = appUserRepository.save(entity);
            return Optional.of(savedUser);
        } catch (DataAccessException ex) {
            log.error("Unable to save AppUser: {}", entity, ex);
            return Optional.empty();
        }
    }

    @Override
    public void delete(AppUser entity) {
        log.info("Deleting AppUser: {}", entity);
        if (appUserRepository.existsById(entity.getId())) {
            appUserRepository.delete(entity);
        }
    }

    @Override
    public Optional<AppUser> update(AppUser entity) {
        log.info("Updating AppUser: {}", entity);
        if (appUserRepository.existsById(entity.getId())) {
            try {
                AppUser updatedUser = appUserRepository.save(entity);
                return Optional.of(updatedUser);
            } catch (DataAccessException ex) {
                log.error("Unable to update AppUser: {}", entity, ex);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading AppUser by username: {}", username);
        Optional<AppUser> appUserOptional = Optional.ofNullable(appUserRepository.findByUsername(username));
        if (appUserOptional.isEmpty()) {
            log.error("AppUser with username {} not found", username);
            throw new UsernameNotFoundException("AppUser with username " + username + " not found.");
        }
        AppUser appUser = appUserOptional.get();
        return new User(appUser.getUsername(), appUser.getPassword(), appUser.getAuthorities());
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }
}

