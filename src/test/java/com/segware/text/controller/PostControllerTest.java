package com.segware.text.controller;

import com.segware.text.dto.request.PostTextDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.post.controller.PostController;
import com.segware.text.post.service.impl.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.segware.text.utils.PostUtils.createFakeDTO;
import static com.segware.text.utils.UserUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    private static final String POST_API_URL_PATH = "/api/v1/post";
    private static final String POST_UP = "up";
    private static final String POST_DOWN = "down";

    private MockMvc mockMvc;

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
        postController = new PostController(postService);
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void testWhenPOSTIsCalledThenAPostShouldBeCreated() throws Exception {
        PostTextDTO expectedPersonDTO = createFakeDTO();
        MessageResponseDTO expectedResponseMessage = createMessageResponse("Successfully registered person", 1L);

        when(postService.createPostText(expectedPersonDTO)).thenReturn(expectedResponseMessage);

        mockMvc.perform(post(POST_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedPersonDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedResponseMessage.getMessage())));
    }


    @Test
    void testWhenGETIsCalledThenPostListShouldBeReturned() throws Exception {
        var expectedValidId = 1L;
        PostTextDTO expectedPersonDTO = createFakeDTO();
        expectedPersonDTO.setId(expectedValidId);

        mockMvc.perform(get(POST_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testWhenGETWithValidIsCalledThenAPostShouldBeReturnedUpVotes() throws Exception {
        var expectedValidId = 1L;
        PostTextDTO expectedPostDTO = createFakeDTO();
        expectedPostDTO.setId(expectedValidId);

        postService.createUpVotes(expectedValidId, POST_UP);

        mockMvc.perform(post(POST_API_URL_PATH + "/" + expectedValidId + "/" + POST_UP)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(POST_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testWhenGETWithValidIsCalledThenAPostShouldBeReturnedDownVotes() throws Exception {
        var expectedValidId = 1L;
        PostTextDTO expectedPostDTO = createFakeDTO();
        expectedPostDTO.setId(expectedValidId);

        postService.createUpVotes(expectedValidId, POST_DOWN);

        mockMvc.perform(post(POST_API_URL_PATH + "/" + expectedValidId + "/" + POST_DOWN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(POST_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private MessageResponseDTO createMessageResponse(String message, Long id) {
        return MessageResponseDTO.builder()
                .message(message + id)
                .build();
    }
}