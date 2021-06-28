package com.segware.text.service.impl;

import com.segware.text.dto.request.PostTextDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.dto.response.PostResponseDTO;
import com.segware.text.exception.PostDuplicateTextException;
import com.segware.text.exception.PostNotFoundException;
import com.segware.text.post.model.Post;
import com.segware.text.post.repository.PostRepository;
import com.segware.text.post.service.impl.PostService;
import com.segware.text.security.UserLoginSecurity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.segware.text.utils.PostUtils.createFakeDTO;
import static com.segware.text.utils.PostUtils.createFakeEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private UserLoginSecurity userLoginSecurity;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void testGivenPostTextDTOThenReturnSuccessSavedMessage() {
        PostTextDTO postDTO = createFakeDTO();
        Post expectedSavedPost = createFakeEntity();

        when(postRepository.save(any(Post.class))).thenReturn(expectedSavedPost);

        MessageResponseDTO successMessage = postService.createPostText(postDTO);

        assertEquals("Successfully registered post", successMessage.getMessage());
    }

    @Test
    void testGivenInvalidPostIdThenThrowException() {
        var invalidPostId = 1L;
        when(postRepository.findById(invalidPostId))
                .thenReturn(Optional.ofNullable(any(Post.class)));

        assertThrows(PostNotFoundException.class, () -> postService.createUpVotes(invalidPostId, "down"));
    }

    @Test
    void testGivenNoDataThenReturnAllPostRegistered() {
        List<Post> expectedRegisteredPost = Collections.singletonList(createFakeEntity());
        PostTextDTO postDTO = createFakeDTO();
        Post post = createFakeEntity();
        postDTO.setId(post.getId());

        when(postRepository.findAll()).thenReturn(expectedRegisteredPost);

        Page<PostResponseDTO> expectedPeopleDTOPage = postService.listAll(Pageable.unpaged());

        assertFalse(expectedPeopleDTOPage.isEmpty());
        assertEquals(expectedPeopleDTOPage.getContent().get(0).getId(), postDTO.getId());
    }

    @Test
    void testGivenValidPostIdAndUpVotesInfoThenReturnSuccess() throws PostNotFoundException {
        var invalidPostId = 2L;

        PostTextDTO postTextDTO = createFakeDTO();

        when(postService.createPostText(postTextDTO)).thenReturn(MessageResponseDTO.builder().build());

        assertThrows(PostNotFoundException.class, () -> postService.createUpVotes(invalidPostId, "up"));

    }

    @Test
    void testGivenPostDuplicateTextInfoThenThrowException() throws PostDuplicateTextException {
        var postId = 1L;

        PostTextDTO postTextDTO = createFakeDTO();

        when(postService.createPostText(postTextDTO)).thenReturn(MessageResponseDTO.builder().build());

        assertThrows(PostDuplicateTextException.class, () -> postService.createPostText(postTextDTO));
    }

}
