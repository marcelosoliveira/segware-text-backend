package com.segware.text.service.impl;

import com.segware.text.dto.request.UpVotesDTO;
import com.segware.text.mapper.PostMapper;
import com.segware.text.security.UserLoginSecurity;
import com.segware.text.upvote.model.UpVotes;
import com.segware.text.upvote.repository.UpVotesRepository;
import com.segware.text.upvote.service.impl.UpVotesService;
import com.segware.text.utils.UpVotesUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpVotesServiceTest {

    private final PostMapper postMapper = PostMapper.INSTANCE;

    @Mock
    private UserLoginSecurity userLoginSecurity;

    @Mock
    private UpVotesRepository upVotesRepository;

    @InjectMocks
    private UpVotesService upVotesService;


    @Test
    void testGivenDataThenReturnAllUpVotesRegistered() {
        List<UpVotes> expectedRegisteredUpVotes = Collections.singletonList(UpVotesUtils.createFakeEntity());
        UpVotesDTO upVotesDTO = UpVotesUtils.createFakeDTO();
        UpVotes upVotes = UpVotesUtils.createFakeEntity();
        upVotesDTO.setId(upVotes.getId());

        when(upVotesRepository.findByUserId(upVotesDTO.getUserId())).thenReturn(expectedRegisteredUpVotes);
        List<UpVotes> expectedUpVotesDTO = upVotesRepository.findByUserId(upVotesDTO.getUserId());
        assertFalse(expectedUpVotesDTO.isEmpty());
        assertEquals(expectedUpVotesDTO.get(0).getId(), upVotesDTO.getId());
    }
}
