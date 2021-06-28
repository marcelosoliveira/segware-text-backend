package com.segware.text.upvote.controller;

import com.segware.text.dto.request.UpVotesDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

public interface UpVotesControllerDocs {

    @ApiOperation(value = "List all votes for a particular user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List Votes ok")
    })
    List<UpVotesDTO> listAllUpVotes();

}
