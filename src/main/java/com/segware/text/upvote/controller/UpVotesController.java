package com.segware.text.upvote.controller;

import com.segware.text.dto.request.UpVotesDTO;
import com.segware.text.upvote.service.impl.UpVotesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userUpVotes")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UpVotesController implements UpVotesControllerDocs {

    private UpVotesService upVotesService;

    @GetMapping
    public List<UpVotesDTO> listAllUpVotes() {
        return this.upVotesService.listAllUpVotesUserLogin();
    }
}
