package com.segware.text.utils;

import com.segware.text.dto.request.UpVotesDTO;
import com.segware.text.post.model.Post;
import com.segware.text.upvote.model.UpVotes;
import com.segware.text.user.model.User;

import java.util.UUID;

import static com.segware.text.utils.UserUtils.createFakeUser;

public class UpVotesUtils {

    private static final Boolean UP_VOTES = true;
    private static final Boolean DOWN_VOTES = false;
    private static final long UP_VOTES_ID = 1L;
    private static final UUID USER_ID = UUID.fromString("2dde6a22-0db6-424b-94eb-7d4105352163");
    private static final Long POST_ID = 1L;
    public static final User USER = createFakeUser();
    public static final Post POST = PostUtils.createFakeEntity();

    public static UpVotesDTO createFakeDTO() {
        return UpVotesDTO.builder()
                .upVotes(UP_VOTES)
                .downVotes(DOWN_VOTES)
                .postId(POST_ID)
                .userId(USER_ID)
                .build();
    }

    public static UpVotes createFakeEntity() {
        return UpVotes.builder()
                .id(UP_VOTES_ID)
                .upVotes(UP_VOTES)
                .downVotes(DOWN_VOTES)
                .post(POST)
                .user(USER)
                .build();
    }
}
