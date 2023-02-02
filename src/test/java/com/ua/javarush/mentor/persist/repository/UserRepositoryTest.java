package com.ua.javarush.mentor.persist.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.ua.javarush.mentor.PersistenceLayerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PersistenceLayerTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    @DataSet(value = "datasets/user/default_user.yml", cleanBefore = true, cleanAfter = true)
    void should_find_user() {
        System.out.println(userRepository.findByEmail("email@email.com").stream().count());
    }

}