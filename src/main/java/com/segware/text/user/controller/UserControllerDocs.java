package com.segware.text.user.controller;

import com.segware.text.dto.request.UserDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "Manager Person")
public interface UserControllerDocs {

    @ApiOperation(value = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Registration ok")
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "Authorization", defaultValue = "Access allowed"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    MessageResponseDTO createUser(UserDTO userDTO);

}
