package com.example.newsblog.service;

import com.example.newsblog.persistence.dto.captcha.CaptchaResponseDto;

public interface CaptchaService {

    CaptchaResponseDto getCaptchaResponseDto(String captchaResponse);

}
