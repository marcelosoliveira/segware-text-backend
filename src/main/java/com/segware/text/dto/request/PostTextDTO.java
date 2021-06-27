package com.segware.text.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTextDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @NotBlank(message = "Text cannot be null or empty")
    @Size(min = 5, max = 255, message = "Enter a minimum of 5 characters and a maximum of 255")
    private String text;

    @ApiModelProperty(hidden = true)
    private String author;

    @NotNull
    @ApiModelProperty(hidden = true)
    private Integer upCount = 0;

    @NotNull
    @ApiModelProperty(hidden = true)
    private Integer downCount = 0;

    @NotNull
    @ApiModelProperty(hidden = true)
    private LocalDate createdAt = LocalDate.now();

}
