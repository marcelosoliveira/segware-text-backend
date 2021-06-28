package com.segware.text.utils;

import com.segware.text.dto.request.PostTextDTO;
import com.segware.text.post.model.Post;
import com.segware.text.user.model.User;

import java.time.LocalDate;
import java.util.Collections;

import static com.segware.text.utils.UserUtils.createFakeUser;

public class PostUtils {

    private static final String TEXT = "Hoje é um ótimo dia";
    private static final String AUTHOR = "Ana Marcia";
    private static final Integer UP_COUNT = 1;
    private static final Integer DOWN_COUNT = 0;
    private static final long POST_ID = 1L;
    public static final LocalDate CREATES_AT = LocalDate.of(2021, 06, 28);
    public static final User USER_LOGIN = createFakeUser();

    public static PostTextDTO createFakeDTO() {
        return PostTextDTO.builder()
                .text(TEXT)
                .author(AUTHOR)
                .upCount(UP_COUNT)
                .downCount(DOWN_COUNT)
                .createdAt(CREATES_AT)
                .build();
    }

    public static Post createFakeEntity() {
        return Post.builder()
                .id(POST_ID)
                .text(TEXT)
                .author(AUTHOR)
                .upCount(UP_COUNT)
                .downCount(DOWN_COUNT)
                .createdAt(CREATES_AT)
                .votes(Collections.singletonList(UpVotesUtils.createFakeEntity()))
                .user(USER_LOGIN)
                .build();
    }

}
