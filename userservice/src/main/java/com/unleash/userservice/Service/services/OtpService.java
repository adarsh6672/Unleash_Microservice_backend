package com.unleash.userservice.Service.services;

import com.unleash.userservice.DTO.UserDto;

public interface OtpService {

    String generateOTP(UserDto user);
    boolean validateOTP(String phoneNumber, String otp);
}
