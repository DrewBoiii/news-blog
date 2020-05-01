package com.example.newsblog.persistence.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class UserProfileDto {

    @NotBlank
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDateTime birth;

}
