package com.example.newsblog.persistence.dto.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserProfileDto {

    @NotBlank
    private Long id;

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    private String birth;

    private MultipartFile photo;

}
