package com.unleash.userservice.Model;



public class AuthenticationResponse {

    private String token;

    public String getRole() {
        return role;
    }

    private String role;

    public AuthenticationResponse(String token , String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }
}

