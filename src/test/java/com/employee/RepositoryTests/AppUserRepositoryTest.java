package com.employee.RepositoryTests;

import com.employee.model.AppUser;
import com.employee.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void testSaveAppUser() {
        AppUser appUser = new AppUser();
        appUser.setUsername("testuser");
        appUser.setPassword("password");
        appUser.setEmail("testuser@example.com");

        AppUser savedAppUser = appUserRepository.save(appUser);

        Assertions.assertNotNull(savedAppUser.getId());
    }

    @Test
    public void testFindOneAppUser() {
        AppUser appUser = new AppUser();
        appUser.setUsername("testuser");
        appUser.setPassword("password");
        appUser.setEmail("testuser@example.com");
        AppUser savedAppUser = appUserRepository.save(appUser);
        Optional<AppUser> foundAppUserOptional = appUserRepository.findById(savedAppUser.getId());
        Assertions.assertTrue(foundAppUserOptional.isPresent());
        AppUser foundAppUser = foundAppUserOptional.get();
        Assertions.assertEquals(savedAppUser.getId(), foundAppUser.getId());
        Assertions.assertEquals(savedAppUser.getUsername(), foundAppUser.getUsername());
        Assertions.assertEquals(savedAppUser.getPassword(), foundAppUser.getPassword());
        Assertions.assertEquals(savedAppUser.getEmail(), foundAppUser.getEmail());
    }

    @Test
    public void testUpdateAppUser() {
        AppUser appUser = new AppUser();
        appUser.setUsername("testuser");
        appUser.setPassword("password");
        appUser.setEmail("testuser@example.com");
        AppUser savedAppUser = appUserRepository.save(appUser);

        savedAppUser.setPassword("newpassword");
        AppUser updatedAppUser = appUserRepository.save(savedAppUser);

        Assertions.assertEquals(savedAppUser.getId(), updatedAppUser.getId());
        Assertions.assertEquals(savedAppUser.getUsername(), updatedAppUser.getUsername());
        Assertions.assertEquals("newpassword", updatedAppUser.getPassword());
        Assertions.assertEquals(savedAppUser.getEmail(), updatedAppUser.getEmail());
    }

    @Test
    public void testDeleteAppUser() {
        AppUser appUser = new AppUser();
        appUser.setUsername("testuser");
        appUser.setPassword("password");
        appUser.setEmail("testuser@example.com");

        AppUser savedAppUser = appUserRepository.save(appUser);

        appUserRepository.delete(savedAppUser);

        Optional<AppUser> deletedAppUserOptional = appUserRepository.findById(savedAppUser.getId());
        Assertions.assertFalse(deletedAppUserOptional.isPresent());
    }

    @Test
    public void testFindAllAppUsers() {
        AppUser appUser1 = new AppUser();
        appUser1.setUsername("testuser1");
        appUser1.setPassword("password1");
        appUser1.setEmail("testuser1@example.com");
        appUserRepository.save(appUser1);

        AppUser appUser2 = new AppUser();
        appUser2.setUsername("testuser2");
        appUser2.setPassword("password2");
        appUser2.setEmail("testuser2@example.com");
        appUserRepository.save(appUser2);

        List<AppUser> allAppUsers = appUserRepository.findAll();
        Assertions.assertEquals(2, allAppUsers.size());
    }

}


