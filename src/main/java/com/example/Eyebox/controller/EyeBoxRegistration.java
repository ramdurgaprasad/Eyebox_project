package com.example.Eyebox.controller;

import com.example.Eyebox.entity.EyeBoxRegistrationEntity;
import com.example.Eyebox.model.EyeBoxRegistrationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Eyebox.service.EyeBoxRegistrationService;

import java.util.List;

@RestController
public class EyeBoxRegistration {

    @Autowired
    private EyeBoxRegistrationService userService;
        // register a new user's account: no all the user information are required
        @PostMapping("/register")
        public ResponseEntity<EyeBoxRegistrationModel> registerNewUserAccount(@RequestBody EyeBoxRegistrationModel registerUserAccountDTO) {
            return new ResponseEntity(userService.registerUserAccount(registerUserAccountDTO), HttpStatus.CREATED);
        }


        @GetMapping("/getusers")
    public ResponseEntity<List<EyeBoxRegistrationEntity>> getUsers() {
            return new ResponseEntity<>(userService.getUser(),HttpStatus.OK);
        }

    }

