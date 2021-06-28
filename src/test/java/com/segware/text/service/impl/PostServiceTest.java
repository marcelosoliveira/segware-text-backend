package com.segware.text.service.impl;

import com.segware.text.dto.request.PostTextDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.exception.PostDuplicateTextException;
import com.segware.text.exception.PostNotFoundException;
import com.segware.text.mapper.PostMapper;
import com.segware.text.post.model.Post;
import com.segware.text.post.repository.PostRepository;
import com.segware.text.post.service.impl.PostService;
import com.segware.text.security.UserLoginSecurity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private final PostMapper postMapper = PostMapper.INSTANCE;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void testGivenPostTextDTOThenReturnSuccessSavedMessage() {
        PostTextDTO postDTO = createFakeDTO();

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

        List<Post> expectedPostDTO = postRepository.findAll();

        assertFalse(expectedPostDTO.isEmpty());
        assertEquals(expectedPostDTO.get(0).getId(), postDTO.getId());
    }

    @Test
    void testGivenValidPostIdAndUpVotesInfoThenReturnSuccess() throws PostNotFoundException {
        var invalidPostId = 2L;

        when(postRepository.findById(invalidPostId))
                .thenReturn(Optional.ofNullable(any(Post.class)));

        assertThrows(PostNotFoundException.class, () -> postService.createUpVotes(invalidPostId, "up"));

    }

    @Test
    void testGivenPostDuplicateTextInfoThenThrowException() throws PostDuplicateTextException {
        var postId = 1L;
        PostTextDTO postTextDTO = createFakeDTO();
        Post post = createFakeEntity();
        postTextDTO.setId(postId);

        when(postRepository.save(post)).thenReturn(any(Post.class));

        assertThrows(PostDuplicateTextException.class, () -> postService.createPostText(postTextDTO));
    }

}
