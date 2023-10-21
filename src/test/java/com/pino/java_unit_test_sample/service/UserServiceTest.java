package com.pino.java_unit_test_sample.service;

import com.pino.java_unit_test_sample.dao.UserDAO;
import com.pino.java_unit_test_sample.exception.BadRequestException;
import com.pino.java_unit_test_sample.model.entity.UserEntity;
import com.pino.java_unit_test_sample.model.model.CreateUserDTO;
import com.pino.java_unit_test_sample.model.model.UserDTO;
import com.pino.java_unit_test_sample.util.IdentityNumberUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks UserService userService;
    @Mock UserDAO userDAO;

    @DisplayName("Find all successfully")
    @Test
    void givenNothing_whenFindAll_thenReturnUserDTOList() {
        var userEntityList = List.of(
            new UserEntity("A260013339", "Andy Wang"),
            new UserEntity("D116312695", "Wendy Chang")
        );
        var expected = List.of(
            new UserDTO("A260013339", "Andy Wang"),
            new UserDTO("D116312695", "Wendy Chang")
        );

        when(userDAO.findAll()).thenReturn(userEntityList);

        assertThat(userService.findAll()).isEqualTo(expected);
    }

    @DisplayName("Create user successfully")
    @Test
    void givenNonexistentIdentityNumber_whenCreateUser_thenNothing() {
        var request = new CreateUserDTO("A260013339", "Andy Wang");
        try (var utilities = Mockito.mockStatic(IdentityNumberUtils.class)) {
            utilities.when(() -> IdentityNumberUtils.isValid(any())).thenReturn(true);
            when(userDAO.findByIdentityNumber(any())).thenReturn(null);
            doNothing().when(userDAO).save(any());

            userService.createUser(request);

            var userEntity = new UserEntity("A260013339", "Andy Wang");
            Mockito.verify(userDAO).save(userEntity);
        }
    }

    @DisplayName("Using existing identity number when creating a user throws an error")
    @Test
    void givenExistingIdentityNumber_whenCreateUser_thenThrowsException() {
        var request = new CreateUserDTO("A260013339", "Andy Wang");
        when(userDAO.findByIdentityNumber(any())).thenReturn(new UserEntity());

        var thrown = assertThrows(
            BadRequestException.class,
            () -> userService.createUser(request)
        );

        assertEquals("User already exists", thrown.getMessage());
        Mockito.verify(userDAO, Mockito.times(0)).save(any());
    }

    @DisplayName("Using invalid identity number when creating a user throws an error")
    @Test
    void givenInvalidIdentityNumber_whenCreateUser_thenThrowsException() {
        var request = new CreateUserDTO("XXX", "Andy Wang");
        try (var utilities = Mockito.mockStatic(IdentityNumberUtils.class)) {
            utilities.when(() -> IdentityNumberUtils.isValid(any())).thenReturn(false);

            var thrown = assertThrows(
                BadRequestException.class,
                () -> userService.createUser(request)
            );

            assertEquals("Identity number is invalid", thrown.getMessage());
            Mockito.verify(userDAO, Mockito.times(0)).save(any());
        }
    }

    @DisplayName("Delete user successfully")
    @Test
    void givenExistingIdentityNumber_whenDeleteUser_thenNothing() {
        var request = "A260013339";
        when(userDAO.findByIdentityNumber(request)).thenReturn(new UserEntity());
        doNothing().when(userDAO).deleteByIdentityNumber(any());

        userService.deleteUser(request);

        Mockito.verify(userDAO).deleteByIdentityNumber(request);
    }

    @DisplayName("Using non-existent identity number when deleting a user throws an error")
    @Test
    void givenNonexistentIdentityNumber_whenDeleteUser_thenThrowsException() {
        var request = "A260013339";
        when(userDAO.findByIdentityNumber(request)).thenReturn(null);

        var thrown = assertThrows(
            BadRequestException.class,
            () -> userService.deleteUser(request)
        );

        assertEquals("User does not exist", thrown.getMessage());
        Mockito.verify(userDAO, Mockito.times(0)).deleteByIdentityNumber(any());
    }
}
