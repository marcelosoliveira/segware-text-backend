package com.segware.text.mapper;

import com.segware.text.dto.request.PostTextDTO;
import com.segware.text.dto.request.UpVotesDTO;
import com.segware.text.dto.request.UserDTO;
import com.segware.text.dto.response.PostResponseDTO;
import com.segware.text.post.model.Post;
import com.segware.text.upvote.model.UpVotes;
import com.segware.text.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd-MM-yyyy")
    PostTextDTO toPostTextDTO(Post post);

    Post toPostModel(PostTextDTO postTextDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "dd-MM-yyyy")
    PostResponseDTO toPostResponseDTO(Post post);

    User toUserModel(UserDTO userDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "postId", source = "post.id")
    UpVotesDTO toUpVotesDTO(UpVotes upVotes);

}
