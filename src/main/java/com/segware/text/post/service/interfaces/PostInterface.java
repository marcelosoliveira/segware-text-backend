package com.segware.text.post.service.interfaces;

import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.post.model.Post;
import com.segware.text.upvote.model.UpVotes;

import java.util.Optional;

public interface PostInterface {

    MessageResponseDTO createMessageResponse(String message);

    void verifyDuplicateText(String text);

    Post verifyExistText(Long id);

    Post validationUpVotes(Post post, String votes);

    void saveUpVotes(UpVotes votes, Optional<UpVotes> userPostUpVotes);

}
