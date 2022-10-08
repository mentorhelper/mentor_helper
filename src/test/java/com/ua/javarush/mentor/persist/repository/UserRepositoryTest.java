package com.ua.javarush.mentor.persist.repository;

import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    void should_save_user() {
        Role newRole = new Role();
        newRole.setName("ADMIN");
        Role role = roleRepository.save(newRole);

        User user = new User();
        user.setId(1L);
        user.setFirstName("John1");
        user.setLastName("Smith");
        user.setCountry("KZ");
        user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        user.setTelegramId(1L);
        user.setTelegramNickname("@johnsmith");
        user.setSalaryPerHour(400);
        user.setSalaryCurrency("USD");
        user.setSecretPhrase("HelloAnton");
        user.setRoleId(role);

        User savedUser = userRepository.save(user);
        User findedUser = userRepository.findById(savedUser.getId()).get();

        assertEquals(user.getFirstName(), findedUser.getFirstName());
        assertEquals(user.getLastName(), findedUser.getLastName());
        assertEquals(user.getSecretPhrase(), findedUser.getSecretPhrase());
    }
}