package com.segware.text.controller;

import com.segware.text.dto.request.UpVotesDTO;
import com.segware.text.mapper.PostMapper;
import com.segware.text.security.UserLoginSecurity;
import com.segware.text.upvote.controller.UpVotesController;
import com.segware.text.upvote.model.UpVotes;
import com.segware.text.upvote.service.impl.UpVotesService;
import com.segware.text.utils.UpVotesUtils;
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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class UpVotesControllerTest {

    private static final String USER_API_URL_PATH = "/api/v1/userUpVotes";

    MockMvc mockMvc;

    private final PostMapper postMapper = PostMapper.INSTANCE;

    @Mock
    private UserLoginSecurity userLoginSecurity;

    @Mock
    private UpVotesService upVotesService;

    @InjectMocks
    private UpVotesController upVotesController;

    @Before
    public void setUp() {
        upVotesController = new UpVotesController(upVotesService);
        mockMvc = MockMvcBuilders.standaloneSetup(upVotesController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }


    @Test
    public void testWhenGETIsCalledThenUpVotesListShouldBeReturned() throws Exception {
        UpVotes upVotes =  UpVotesUtils.createFakeEntity();
        UpVotesDTO expectedUpVotesDTO = UpVotesUtils.createFakeDTO();
        expectedUpVotesDTO.setId(upVotes.getId());
        List<UpVotesDTO> expectedUpVotesDTOList = Collections.singletonList(expectedUpVotesDTO);

        when(upVotesService.listAllUpVotesUserLogin()).thenReturn(expectedUpVotesDTOList);

        mockMvc.perform(get(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].upVotes", is(true)))
                .andExpect(jsonPath("$[0].downVotes", is(false)))
                .andExpect(jsonPath("$[0].postId", is(expectedUpVotesDTO.getPostId().intValue())))
                .andExpect(jsonPath("$[0].userId", is(expectedUpVotesDTO.getUserId().toString())));
    }
}
