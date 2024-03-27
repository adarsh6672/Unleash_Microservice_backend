package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Service.services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceImp implements OtpService {

    private final EmailServiceImp emailServiceImp;

    @Autowired
    public OtpServiceImp(EmailServiceImp emailServiceImp) {
        this.emailServiceImp = emailServiceImp;
    }

    @Override
    public String generateOTP(UserDto user) {
        Random random = new Random();
        String otp=  String.format("%06d", random.nextInt(999999));
        user.setOtp(otp);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("OTP Verification");
        mailMessage.setText("Dear "+user.getFullname()+" , Your OTP is "+otp+" for Unleash. Thanks , Unleash Team");
        try {
            emailServiceImp.sendEmail(mailMessage);
        }catch (Exception e){
            System.out.println("email sending failed");
        }
        return otp;
    }

    @Override
    public boolean validateOTP(String sent, String recieve) {
        return sent.equals(recieve);
    }
}
