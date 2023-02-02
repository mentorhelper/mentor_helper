package com.ua.javarush.mentor.persist.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.ua.javarush.mentor.PersistenceLayerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@PersistenceLayerTest
@DisplayName("RoleRepository")
class RoleRepositoryTest {

    private static final String VALUE_IN_DATABASE = "ME";
    private static final String VALUE_NOT_IN_DATABASE = "NOT_ME";
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("should return role by name")
    @DataSet(value = "datasets/role/default_role.yml", cleanAfter = true)
    void should_return_4_roles() {
        assertEquals(1, roleRepository.findAll().size());
    }

    @Test
    @DisplayName("should return role by name")
    @DataSet(value = "datasets/role/basic_role.yml", cleanAfter = true)
    void should_return_5_role() {
        assertEquals(5, roleRepository.findAll().size());
    }

    @Test
    @DisplayName("should return role by id")
    @DataSet(cleanBefore = true, cleanAfter = true)
    void should_return_0_roles() {
        assertEquals(0, roleRepository.findAll().size());
    }

    @Test
    @DisplayName("should return role by id")
    @DataSet(value = "datasets/role/default_role.yml", cleanAfter = true)
    void should_find_by_name() {
        assertEquals(VALUE_IN_DATABASE, roleRepository.findByName(VALUE_IN_DATABASE).get().getName());
    }

    @Test
    @DisplayName("should return role by id")
    @DataSet(value = "datasets/role/default_role.yml", cleanAfter = true)
    void should_throw_exception() {
        assertThrows(Exception.class, () -> roleRepository.findByName(VALUE_NOT_IN_DATABASE).get().getId());
    }

    @Test
    @DisplayName("should return role by id")
    @DataSet(value = "datasets/role/default_role.yml", cleanAfter = true)
    void should_throw_exception_2() {
        assertThrows(Exception.class, () -> roleRepository.findByName(null).get().getName());
    }

    @Test
    @DisplayName("should return role by id")
    @DataSet(value = "datasets/role/default_role.yml")
    void should_find_by_id() {
        assertEquals(VALUE_IN_DATABASE, roleRepository.findById(5L).get().getName());
    }
}