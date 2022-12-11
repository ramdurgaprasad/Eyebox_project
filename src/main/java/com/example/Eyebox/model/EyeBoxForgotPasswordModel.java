package com.example.Eyebox.model;


import lombok.Data;

@Data
public class EyeBoxForgotPasswordModel {

    private  String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
