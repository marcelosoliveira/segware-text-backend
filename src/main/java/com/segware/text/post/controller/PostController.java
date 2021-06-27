package com.segware.text.post.controller;

import com.segware.text.dto.request.PostTextDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.dto.response.PostResponseDTO;
import com.segware.text.post.service.impl.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/post")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class PostController implements PostControllerDocs {

    private PostService postService;

    @Override
    @PostMapping
    public MessageResponseDTO createPost(@Valid @RequestBody PostTextDTO postTextDTO) {
        return this.postService.createPostText(postTextDTO);
    }

    @Override
    @GetMapping
    public Page<PostResponseDTO> listAllPost(@PageableDefault(sort = "id") @ApiIgnore Pageable pageable) {
        return this.postService.listAll(pageable);
    }

    @Override
    @PostMapping("/{id}/{upVotes}")
    public void upVotes(@PathVariable Long id, @PathVariable String upVotes) {
        this.postService.createUpVotes(id, upVotes);
    }
}
