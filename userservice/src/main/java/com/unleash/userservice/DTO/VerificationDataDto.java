package com.unleash.userservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class VerificationDataDto {

    private int qualificationId ;
    private String dob;
    private int genderId;
    private int languageId;
    private int yoe;
    private int specializationId;



}
