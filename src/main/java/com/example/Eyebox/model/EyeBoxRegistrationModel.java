package com.example.Eyebox.model;

import com.example.Eyebox.entity.EyeBoxRegistrationEntity;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class EyeBoxRegistrationModel implements Serializable {

    private Long id;

    private String username;

    private String password;

    private Long mobileNumber;

    private String location;

    private String gender;

}
