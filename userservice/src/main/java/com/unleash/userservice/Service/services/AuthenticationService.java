package com.unleash.userservice.Service.services;

import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.AuthenticationResponse;
import com.unleash.userservice.Model.User;

public interface AuthenticationService {


    AuthenticationResponse register(UserDto request);

    AuthenticationResponse authenticate(User request);

    boolean isEmailExisting(String email);
}
