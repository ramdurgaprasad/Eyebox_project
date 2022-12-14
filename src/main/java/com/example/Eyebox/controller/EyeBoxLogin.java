package com.example.Eyebox.controller;


import com.example.Eyebox.entity.EyeBoxRegistrationEntity;
import com.example.Eyebox.model.EyeBoxForgotPasswordModel;
import com.example.Eyebox.model.EyeBoxRegistrationModel;
import com.example.Eyebox.model.LoginRequestDTO;
import com.example.Eyebox.model.ResetPassword;
import com.example.Eyebox.service.EyeBoxRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(value = "/login")
@Slf4j
public class EyeBoxLogin {

    private EyeBoxRegistrationModel userDTO;
    private LoginRequestDTO loginRequestDTO;
    @Autowired
    private EyeBoxRegistrationService userService;

    @PostMapping("/login")
    public ResponseEntity<EyeBoxRegistrationModel> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        EyeBoxRegistrationEntity user = userService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        return new ResponseEntity(user, HttpStatus.OK);
    }


    @PostMapping("/changepassword/{id}")
    public ResponseEntity<String> login(@RequestBody EyeBoxForgotPasswordModel passwordModel, @PathVariable Long id) {
        String res = userService.forgot(passwordModel, id);
        return new ResponseEntity(res, HttpStatus.OK);
    }


    @PostMapping("/resetpassword/{id}")
    public ResponseEntity<String> login(@RequestBody ResetPassword passwordModel, @PathVariable Long id) {
        String res = userService.resetPassword(passwordModel, id);
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
