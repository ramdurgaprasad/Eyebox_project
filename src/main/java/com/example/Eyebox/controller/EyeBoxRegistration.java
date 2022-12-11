package com.example.Eyebox.controller;

import com.example.Eyebox.model.EyeBoxRegistrationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Eyebox.service.EyeBoxRegistrationService;

@RestController
@RequestMapping(value = "/users")
public class EyeBoxRegistration {

    @Autowired
    private EyeBoxRegistrationService userService;
        // register a new user's account: no all the user information are required
        @PostMapping("/register")
        public ResponseEntity<EyeBoxRegistrationModel> registerNewUserAccount(@RequestBody EyeBoxRegistrationModel registerUserAccountDTO) {
            return new ResponseEntity(new EyeBoxRegistrationModel(userService.registerUserAccount(registerUserAccountDTO)), null, HttpStatus.CREATED);
        }

    }

