package com.example.newsblog.persistence.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class UserRolesUpdateDto {

    @NotBlank
    private Long id;

    private List<String> roles;

}
