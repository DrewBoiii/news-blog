package com.example.newsblog.persistence.dto.user;

import com.example.newsblog.constraint.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String old;

    @ValidPassword
    @NotBlank
    private String password;

    @ValidPassword
    @NotBlank
    private String confirm;

}
