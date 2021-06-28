package com.segware.text.controller;

import com.segware.text.dto.request.UserDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.user.controller.UserController;
import com.segware.text.user.service.impl.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.segware.text.utils.UserUtils.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String USER_API_URL_PATH = "/api/v1/user";

    MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    public void createUsersCorrectStatusCode201() throws Exception {
        UserDTO userDTO = createFakeUserDTO();

        when(userService.createUserService(userDTO)).thenReturn(MessageResponseDTO.builder().build());

        mockMvc.perform(post(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(status().isCreated());

        verify(userService).createUserService(userDTO);
    }

//    @Test
//    public void testWhenGETIsCalledThenUserListShouldBeReturned() throws Exception {
//        User user =  createFakeUser();
//        UserDTO expectedUserDTO = createFakeUserDTO();
//        expectedUserDTO.setId(user.getId());
//        List<UserDTO> expectedUserDTOList = Collections.singletonList(expectedUserDTO);
//
//        when(userService.findUsersAll()).thenReturn(expectedUserDTOList);
//
//        mockMvc.perform(get(USER_API_URL_PATH)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].username", is("jow")))
//                .andExpect(jsonPath("$[0].password", is("12345678")));
//    }

}
