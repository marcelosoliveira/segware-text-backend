package com.segware.text.post.service.impl;

import com.segware.text.dto.request.PostTextDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.dto.response.PostResponseDTO;
import com.segware.text.exception.PostDuplicateTextException;
import com.segware.text.exception.PostNotFoundException;
import com.segware.text.mapper.PostMapper;
import com.segware.text.post.model.Post;
import com.segware.text.post.repository.PostRepository;
import com.segware.text.post.service.interfaces.PostInterface;
import com.segware.text.security.UserLoginSecurity;
import com.segware.text.upvote.model.UpVotes;
import com.segware.text.upvote.repository.UpVotesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService implements PostInterface {

    private static final String UP_VOTES = "up";
    private static final String DOWN_VOTES = "down";
    private final PostMapper postMapper = PostMapper.INSTANCE;

    private UserLoginSecurity userLoginSecurity;

    private PostRepository postRepository;

    private UpVotesRepository upVotesRepository;

    public MessageResponseDTO createPostText(PostTextDTO postTextDTO) {
        verifyDuplicateText(postTextDTO.getText());
        Post post = this.postMapper.toPostModel(postTextDTO);
        post.setUser(userLoginSecurity.getLoginUser());
        post.setAuthor(userLoginSecurity.getLoginUser().getName());
        this.postRepository.save(post);
        return createMessageResponse("Post performed successfully");
    }

    public Page<PostResponseDTO> listAll(Pageable pageable) {
        Page<Post> postModels = this.postRepository.findAll(pageable);
        return postModels.map(this.postMapper::toPostResponseDTO);
    }

    public void createUpVotes(Long id, String upVotes) {
        Post post = verifyExistText(id);
        Post postSave = validationUpVotes(post, upVotes);
        this.postRepository.save(postSave);
    }

    @Override
    public MessageResponseDTO createMessageResponse(String message) {
        return MessageResponseDTO
                .builder()
                .message(message)
                .build();
    }

    @Override
    public void verifyDuplicateText(String text) {
        Optional<Post> post = this.postRepository.findByText(text);
        if (post.isPresent()) {
            throw  new PostDuplicateTextException("Post cannot be duplicated");
        }
    }

    @Override
    public Post verifyExistText(Long id) {
        return this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found! ID: " + id));
    }

    @Override
    public Post validationUpVotes(Post post, String upVotes) {
        UpVotes votes = new UpVotes();
        votes.setPost(post);
        votes.setUser(this.userLoginSecurity.getLoginUser());
        Optional<UpVotes> userPostUpVotes = this.upVotesRepository
                .findByUserIdAndPostId(votes.getUser().getId(), votes.getPost().getId());
        if (upVotes.equals(UP_VOTES)) {
            votes.setDownVotes(false);
            if (userPostUpVotes.isPresent()) {
                post.setDownCount(post.getDownCount() > 0 ? post.getDownCount() - 1 : 0);
            }
            votes.setUpVotes(true);
            post.setUpCount(post.getUpCount() + 1);
        } else if (upVotes.equals(DOWN_VOTES)) {
            votes.setUpVotes(false);
            if (userPostUpVotes.isPresent()) {
                post.setUpCount(post.getUpCount() > 0 ? post.getUpCount() - 1 : 0);
            }
            votes.setDownVotes(true);
            post.setDownCount(post.getDownCount() + 1);
        }
        saveUpVotes(votes, userPostUpVotes);
        return post;
    }

    @Override
    public void saveUpVotes(UpVotes votes, Optional<UpVotes> userPostUpVotes) {
        if (userPostUpVotes.isPresent()) {
            userPostUpVotes.get().setUpVotes(votes.getUpVotes());
            userPostUpVotes.get().setDownVotes(votes.getDownVotes());
            this.upVotesRepository.save(userPostUpVotes.get());
        } else {
            this.upVotesRepository.save(votes);
        }
    }

}
