package com.example.newsblog.persistence.dto.captcha;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponseDto {

    @NotBlank
    private boolean success;

    @NotBlank
    private String hostname;

    @JsonAlias("error-codes")
    private Set<String> errorCodes;

    @NotBlank
    private LocalDateTime challenge_ts;

}
