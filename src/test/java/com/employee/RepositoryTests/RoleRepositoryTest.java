package com.employee.RepositoryTests;

import com.employee.model.Role;
import com.employee.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        Role savedRole = roleRepository.save(role);

        Assertions.assertNotNull(savedRole.getId());
    }

    @Test
    public void testFindOneRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        Role savedRole = roleRepository.save(role);

        Optional<Role> foundRoleOptional = roleRepository.findById(savedRole.getId());
        Assertions.assertTrue(foundRoleOptional.isPresent());

        Role foundRole = foundRoleOptional.get();
        Assertions.assertEquals(savedRole.getId(), foundRole.getId());
        Assertions.assertEquals(savedRole.getName(), foundRole.getName());
    }

    @Test
    public void testUpdateRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        Role savedRole = roleRepository.save(role);

        savedRole.setName("ROLE_USER");
        Role updatedRole = roleRepository.save(savedRole);

        Assertions.assertEquals(savedRole.getId(), updatedRole.getId());
        Assertions.assertEquals("ROLE_USER", updatedRole.getName());
    }

    @Test
    public void testDeleteRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        Role savedRole = roleRepository.save(role);

        roleRepository.delete(savedRole);

        Optional<Role> deletedRoleOptional = roleRepository.findById(savedRole.getId());
        Assertions.assertFalse(deletedRoleOptional.isPresent());
    }

    @Test
    public void testFindAllRoles() {
        Role role1 = new Role();
        role1.setName("ROLE_ADMIN");
        roleRepository.save(role1);

        Role role2 = new Role();
        role2.setName("ROLE_USER");
        roleRepository.save(role2);

        Set<Role> allRoles = new HashSet<>(roleRepository.findAll());
        Assertions.assertEquals(2, allRoles.size());
    }

}
