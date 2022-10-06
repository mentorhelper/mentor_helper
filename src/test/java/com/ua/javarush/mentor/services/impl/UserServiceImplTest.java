package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.persist.repository.UserRepository;
import com.ua.javarush.mentor.services.RoleService;
import com.ua.javarush.mentor.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Test
    void should_success_create_user() throws GeneralException {
        UserCommand userCommand = new UserCommand();
        userCommand.setFirstName("John");
        userCommand.setLastName("Smith");
        userCommand.setCountry("KZ");
        userCommand.setTelegramNickname("@johnsmith");
        userCommand.setSalaryPerHour(400);
        userCommand.setSalaryCurrency("USD");
        userCommand.setRoleId(1L);

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setCountry("KZ");
        user.setTelegramNickname("@johnsmith");
        user.setSalaryPerHour(400);
        user.setSalaryCurrency("USD");
        user.setRoleId(new Role());

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(roleService.fetchRole(1L)).thenReturn(new Role());

        UserDTO userDTO = userService.createUser(userCommand);

        assertEquals(userCommand.getFirstName(), userDTO.getFirstName());
        assertEquals(userCommand.getLastName(), userDTO.getLastName());
    }
}