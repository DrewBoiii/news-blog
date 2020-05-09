package com.example.newsblog.service.impl;

import com.example.newsblog.persistence.dto.captcha.CaptchaResponseDto;
import com.example.newsblog.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    private final RestTemplate restTemplate;

    public CaptchaServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CaptchaResponseDto getCaptchaResponseDto(String captchaResponse) {
        String secretKey = System.getenv(secret);
        String url = String.format(CAPTCHA_URL, secretKey, captchaResponse);
        return restTemplate.postForObject(url, Optional.empty(), CaptchaResponseDto.class);
    }
}
