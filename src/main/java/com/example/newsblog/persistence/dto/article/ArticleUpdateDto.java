package com.example.newsblog.persistence.dto.article;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleUpdateDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
