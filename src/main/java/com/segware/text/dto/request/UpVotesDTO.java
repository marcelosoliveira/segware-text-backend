package com.segware.text.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpVotesDTO {

    @NotNull
    private Boolean upVotes;

    @NotNull
    private Boolean downVotes;

    @NotNull
    private Long postId;

    @NotNull
    private UUID userId;

}
