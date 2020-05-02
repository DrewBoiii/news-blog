package com.example.newsblog.persistence.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserProfileDto {

    @NotBlank
    private Long id;

    private String firstName;

    private String lastName;

    private String birth;

}
