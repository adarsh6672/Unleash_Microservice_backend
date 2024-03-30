package com.unleash.userservice.controller;

import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private UserRepository repository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/admin/fetchusers")
    public ResponseEntity<?> adminDashboard(){

        List<User> users= repository.findAll();
        return ResponseEntity.ok(users);
    }

}
