package com.segware.text.dto.response;

import com.segware.text.dto.request.UpVotesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostResponseDTO {

    private Long id;
    private String author;
    private String text;
    private Integer upCount;
    private Integer downCount;
    private List<UpVotesDTO> votes;
    private String createdAt;

}
