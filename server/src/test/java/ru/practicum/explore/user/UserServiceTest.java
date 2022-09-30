package ru.practicum.explore.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.models.user.dto.UserDto;
import ru.practicum.explore.services.admin.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

    private final UserService service;

    @Autowired
    UserServiceTest(UserService service) {
        this.service = service;
    }

    UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder().id(1L).name("User").email("user@user.com").build();
    }

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @Test
    void testAddNewUser() {
        final UserDto dto = service.addNewUser(userDto);
        assertEquals(dto, userDto);
    }

    @Test
    void testGetAllUsers() {
        final UserDto dto = service.addNewUser(userDto);
        final UserDto dto1 = service.addNewUser(userDto.toBuilder().id(2L).name("user2").email("user2@user.com").build());
        assertEquals(List.of(dto, dto1), service.getUsers(null, 0, 10));
    }

    @Test
    void testDeleteUser() {
        final UserDto dto = service.addNewUser(userDto);
        final UserDto dto1 = service.addNewUser(userDto.toBuilder().id(2L).name("user2").email("user2@user.com").build());
        service.deleteUser(dto.getId());
        assertEquals(List.of(dto1), service.getUsers(null, 0, 10));
    }

    @Test
    void testBanUser() {
        final UserDto dto = service.addNewUser(userDto);
        service.ban(dto.getId());
        assertTrue(service.getUsers(null, 0, 10).get(0).isBanned());
    }

    @Test
    void testUnbanUser() {
        final UserDto dto = service.addNewUser(userDto);
        service.unban(dto.getId());
        assertFalse(service.getUsers(null, 0, 10).get(0).isBanned());
    }
}
