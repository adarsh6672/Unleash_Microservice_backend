package com.unleash.userservice.Service;

import com.unleash.userservice.DTO.SelectionResponse;
import com.unleash.userservice.DTO.VerificationDataDto;
import com.unleash.userservice.Model.CounselorData;
import com.unleash.userservice.Model.Language;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.*;
import com.unleash.userservice.Service.services.CounselorService;
import com.unleash.userservice.Service.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class CounselorServiceImp implements CounselorService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final CounselorDateRepository counselorDateRepository;
    private final QualificationRepository qualificationRepository;
    private final LanguageRepository languageRepository;
    private final GenderRepository genderRepository;
    private final SpecializationRepository specializationRepository;

    private final String PATH = "/home/adarsh/BROTOTYPE/Unleash_App/userservice/src/main/resources/static/CounselorDocuments";

    @Autowired
    public CounselorServiceImp(JwtService jwtService, UserRepository userRepository, CounselorDateRepository counselorDateRepository, QualificationRepository qualificationRepository, LanguageRepository languageRepository, GenderRepository genderRepository, SpecializationRepository specializationRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.counselorDateRepository = counselorDateRepository;
        this.qualificationRepository = qualificationRepository;
        this.languageRepository = languageRepository;
        this.genderRepository = genderRepository;
        this.specializationRepository = specializationRepository;
    }

    @Override
    public boolean saveDocuments(MultipartFile qualification, MultipartFile experience, MultipartFile profilePic, String token) throws IOException {
        String  userName = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByUsername(userName).orElseThrow();
        CounselorData counselorData= counselorDateRepository.findByUser(user).orElseThrow();
        try {
            String qualpath=uploadFile("qualification",qualification);
            String expPath=uploadFile("experience",experience);
            String photo = uploadFile("profilePhoto",profilePic);
            counselorData.setExperienceProof(uploadFile("experience",experience));
            counselorData.setQualificationProof(uploadFile("qualification",qualification));
            user.setProfilePic(uploadFile("profilePhoto",profilePic));
            counselorDateRepository.save(counselorData);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public String uploadFile(String type, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        file.transferTo(new File(PATH+type+fileName));
        return PATH+type+fileName;
    }

    @Override
    public boolean uploadData(VerificationDataDto data, String token){
        try {
            String  userName = jwtService.extractUsername(token.substring(7));
            User user = userRepository.findByUsername(userName).orElseThrow();
            CounselorData counselorData= counselorDateRepository.findByUser(user).orElseThrow();
            counselorData.setQualification(qualificationRepository.findById(data.getQualificationId()).orElseThrow());
          /*  counselorData.setLanguages((Set<Language>) languageRepository.findById(data.getLanguageId()).orElseThrow());*/
            counselorData.setSpecialization(specializationRepository.getReferenceById(data.getSpecializationId()));
            counselorData.setUploadedOn(LocalDateTime.now());
            counselorData.setYoe(data.getYoe());
            counselorData.setGender(genderRepository.findById(data.getGenderId()).orElseThrow());

            List<Integer> langIds = data.getLanguages();
            Set<Language> la= counselorData.getLanguages();
            for(Integer language : langIds){
                la.add(languageRepository.findById(language).orElseThrow());
            }
            counselorData.setLanguages(la);
            counselorDateRepository.save(counselorData);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public SelectionResponse getSelectionData(){
        SelectionResponse selectionResponse= new SelectionResponse();
        selectionResponse.setLanguages(languageRepository.findAll());
        selectionResponse.setQualifications(qualificationRepository.findAll());
        selectionResponse.setSpecializations(specializationRepository.findAll());
        return selectionResponse;
    }

}