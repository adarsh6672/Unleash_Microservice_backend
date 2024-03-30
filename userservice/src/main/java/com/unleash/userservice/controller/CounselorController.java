package com.unleash.userservice.controller;

import com.unleash.userservice.DTO.VerificationDataDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/counselor")
public class CounselorController {


    @PostMapping(value = "/documentupload" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> counselorVerification(@RequestPart("qualification") MultipartFile qualification,
                                                   @RequestPart("experience") MultipartFile experience,
                                                   @RequestPart("profilePic") MultipartFile profilePic){

        return ResponseEntity.ok().body("uploaded successfully");
    }

    @PostMapping("/dataupload")
    public ResponseEntity<?> dataVerification(@RequestBody VerificationDataDto dto){
        return ResponseEntity.ok("uploaded success");
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestHeader("Authorization") String headerToken){
        System.out.println(headerToken);
        return ResponseEntity.ok("test passed");
    }

}
