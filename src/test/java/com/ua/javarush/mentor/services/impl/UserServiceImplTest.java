package com.ua.javarush.mentor.services.impl;

import com.ua.javarush.mentor.command.UserCommand;
import com.ua.javarush.mentor.command.UserPermissionCommand;
import com.ua.javarush.mentor.dto.PageDTO;
import com.ua.javarush.mentor.dto.UserDTO;
import com.ua.javarush.mentor.exceptions.GeneralException;
import com.ua.javarush.mentor.persist.model.Role;
import com.ua.javarush.mentor.persist.model.User;
import com.ua.javarush.mentor.persist.repository.UserRepository;
import com.ua.javarush.mentor.services.RoleService;
import com.ua.javarush.mentor.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


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

    @Test
    void should_return_exception_when_create_user_with_bad_user_data() {
        assertThrows(Exception.class, () -> userService.createUser(new UserCommand()));
    }

    @Test
    void should_return_page_with_users() {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John1");
        user1.setLastName("Smith");
        user1.setCountry("KZ");
        user1.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        user1.setTelegramId(1L);
        user1.setTelegramNickname("@johnsmith");
        user1.setSalaryPerHour(400);
        user1.setSalaryCurrency("USD");
        user1.setRoleId(new Role());
        User user2 = new User();
        user2.setId(1L);
        user2.setFirstName("John2");
        user2.setLastName("Smith");
        user2.setCountry("KZ");
        user2.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        user2.setTelegramId(1L);
        user2.setTelegramNickname("@johnsmith");
        user2.setSalaryPerHour(400);
        user2.setSalaryCurrency("USD");
        user2.setRoleId(new Role());
        Page<User> page = new PageImpl<>(List.of(user1, user2));

        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        PageDTO<UserDTO> userDTOList = userService.getAllUsers(0, 5, "id");

        assertThat(userDTOList.getContent()
                .stream()
                .map(UserDTO::getFirstName)
                .collect(Collectors.toList()), hasItems("John1", "John2"));
    }

    @Test
    void should_return_user_by_id() throws GeneralException {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John1");
        user1.setLastName("Smith");
        user1.setCountry("KZ");
        user1.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        user1.setTelegramId(1L);
        user1.setTelegramNickname("@johnsmith");
        user1.setSalaryPerHour(400);
        user1.setSalaryCurrency("USD");
        user1.setRoleId(new Role());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        UserDTO userDTO = userService.getUserById(1L);

        assertEquals(user1.getFirstName(), userDTO.getFirstName());
        assertEquals(user1.getLastName(), userDTO.getLastName());
    }

    @Test
    void should_return_exception_when_user_id_not_found() {
        assertThrows(GeneralException.class, () -> userService.getUserById(1L));
    }

    @Test
    void should_remove_user() throws GeneralException {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John1");
        user1.setLastName("Smith");
        user1.setCountry("KZ");
        user1.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        user1.setTelegramId(1L);
        user1.setTelegramNickname("@johnsmith");
        user1.setSalaryPerHour(400);
        user1.setSalaryCurrency("USD");
        user1.setRoleId(new Role());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        userService.removeUser(1L);

        verify(userRepository).deleteById(anyLong());
    }

    @Test
    void should_change_user_permission() throws GeneralException {
        User user1 = Mockito.spy(new User());
        user1.setId(1L);
        user1.setFirstName("John1");
        user1.setLastName("Smith");
        user1.setCountry("KZ");
        user1.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
        user1.setTelegramId(1L);
        user1.setTelegramNickname("@johnsmith");
        user1.setSalaryPerHour(400);
        user1.setSalaryCurrency("USD");
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        user1.setRoleId(role);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(roleService.fetchRole(2L)).thenReturn(role);

        UserPermissionCommand userPermissionCommand = new UserPermissionCommand();
        userPermissionCommand.setUserId(1L);
        userPermissionCommand.setRoleId(2L);
        userService.changePermission(userPermissionCommand);

        verify(roleService).fetchRole(anyLong());
        verify(user1, atLeast(1)).setRoleId(any(Role.class));
    }
}