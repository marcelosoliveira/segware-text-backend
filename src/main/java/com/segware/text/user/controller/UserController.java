package com.segware.text.user.controller;

import com.segware.text.dto.request.UserDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.user.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController implements UserControllerDocs {

    private UserService userService;

    @PostMapping("/user")
    public MessageResponseDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return this.userService.createUserService(userDTO);
    }

}
