package com.segware.text.upvote.service.impl;

import com.segware.text.dto.request.UpVotesDTO;
import com.segware.text.mapper.PostMapper;
import com.segware.text.security.UserLoginSecurity;
import com.segware.text.upvote.model.UpVotes;
import com.segware.text.upvote.repository.UpVotesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UpVotesService {

    private final PostMapper postMapper = PostMapper.INSTANCE;

    private UpVotesRepository upVotesRepository;

    private UserLoginSecurity userLoginSecurity;

    public List<UpVotesDTO> listAllUpVotesUserLogin() {
        UUID userId = this.userLoginSecurity.getLoginUser().getId();
        List<UpVotes> allById = this.upVotesRepository.findByUserId(userId);
        return allById.stream()
                .map(postMapper::toUpVotesDTO)
                .collect(Collectors.toList());
    }

}
