package com.example.newsblog.persistence.dto.article;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ArticleUpdateDto {

    @NotBlank
    private Long id;

    @Size(max = 255)
    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
