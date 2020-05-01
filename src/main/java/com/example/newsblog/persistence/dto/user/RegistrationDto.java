package com.example.newsblog.persistence.dto.user;

import com.example.newsblog.constraint.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationDto {

    @Min(value = 4, message = "Username should be at least 4 characters long")
    @Max(value = 255)
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @ValidPassword
    @NotBlank
    private String password;

    @ValidPassword
    @NotBlank
    private String confirm;

}
