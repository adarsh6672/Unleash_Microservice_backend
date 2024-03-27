package com.unleash.userservice.controller;


import com.unleash.userservice.DTO.OtpDto;
import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.AuthenticationResponse;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Service.AuthenticationServiceImp;
import com.unleash.userservice.Service.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationServiceImp authService;
    private final OtpService otpService;
    private UserDto req;

    @Autowired
    public AuthenticationController(AuthenticationServiceImp authService, OtpService otpService) {
        this.authService = authService;
        this.otpService = otpService;
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto request){
        req= request;
        if(authService.isEmailExisting(request.getEmail())){
            return ResponseEntity.badRequest().body("email id already existing");
        }
        String sent = otpService.generateOTP(req);
        return ResponseEntity.ok().body("otp send to mail id  "+sent);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("otp/verify")
    public ResponseEntity<?> verifyotp(@RequestBody OtpDto otp){
        System.out.println(otp.getOtp()+"---------------------");
        if(otp.getOtp().equals(req.getOtp())){
            return ResponseEntity.ok(authService.register(req));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("otp verification failed");
    }
}
