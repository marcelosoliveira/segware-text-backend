package com.segware.text.post.controller;

import com.segware.text.dto.request.PostTextDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.dto.response.PostResponseDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface PostControllerDocs {

    @ApiOperation(value = "Post texts")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Post ok"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @ResponseStatus(HttpStatus.CREATED)
    MessageResponseDTO createPost(PostTextDTO postTextDTO);

    @ApiOperation(value = "List all posts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List post ok")
    })
    @ResponseStatus(HttpStatus.OK)
    Page<PostResponseDTO> listAllPost(Pageable pageable);

    @ApiOperation(value = "UpVotes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "UpVotes ok")
    })
    @ApiImplicitParam(name = "upVotes", allowMultiple = true,  dataType = "string",
            allowableValues = "up, down", required = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void upVotes(Long id, String vote);

}
