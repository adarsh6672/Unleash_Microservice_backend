package com.unleash.userservice.Service.services;

import com.unleash.userservice.Model.AuthenticationResponse;
import com.unleash.userservice.Model.User;

public interface AuthenticationService {

    AuthenticationResponse register(User request);

    AuthenticationResponse authenticate(User request);
}
