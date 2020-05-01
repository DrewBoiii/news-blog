package com.example.newsblog.persistence.dto.article;

import com.example.newsblog.persistence.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleSaveDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private User user;

}
