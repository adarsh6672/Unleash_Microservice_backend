package com.unleash.userservice.DTO;

import com.unleash.userservice.Model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {


    private String username;

    private String fullname;

    private String phone;

    private String email;

    private String password;

    private Role role;

    private String otp;

}
