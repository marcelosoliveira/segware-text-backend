package com.segware.text.service.impl;

import com.segware.text.dto.request.UserDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.exception.UserNameExistsException;
import com.segware.text.user.model.User;
import com.segware.text.user.repository.UserRepository;
import com.segware.text.user.service.impl.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.segware.text.utils.UserUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testUserDTOThenReturnSuccessSaveMessage() {
        UserDTO userDTO = createFakeUserDTO();

        MessageResponseDTO successMessage = userService.createUserService(userDTO);

        assertEquals("User created successfully", successMessage.getMessage());
    }

    @Test
    void testVerifyUserNameExistException() throws UserNameExistsException {
        User user = new User();
        user.setUsername("jow");
        UserDTO userDTO = createFakeUserDTO();
        assertThrows(UserNameExistsException.class, () ->
                userNameExist(user.getUsername(), userDTO.getUsername()));
    }

    @Test
    void testVerifyUserNameDuplicateUnique() {
        User user = createFakeUser();
        UserDTO userDTO = createFakeUserDTO();
        userDTO.setId(user.getId());

        when(userRepository.save(user)).thenReturn(user);
        when(userService.createUserService(userDTO)).thenReturn(MessageResponseDTO.builder().build());

        assertThrows(UserNameExistsException.class, () -> userService.createUserService(userDTO));
    }

}
