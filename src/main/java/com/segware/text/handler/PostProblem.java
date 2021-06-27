package com.segware.text.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostProblem {

    private Integer status;
    private LocalDateTime dateTime;
    private String title;
    private String message;

}
