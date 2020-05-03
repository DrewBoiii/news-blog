package com.example.newsblog.persistence.dto.article;

import com.example.newsblog.persistence.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ArticleSaveDto {

    @Size(max = 255)
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private User user;

}
