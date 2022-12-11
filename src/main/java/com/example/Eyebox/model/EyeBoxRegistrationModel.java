package com.example.Eyebox.model;

import com.example.Eyebox.entity.EyeBoxRegistrationEntity;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class EyeBoxRegistrationModel implements Serializable {
        public EyeBoxRegistrationModel(EyeBoxRegistrationEntity user) {
            if (user != null) {
                this.id= user.getId();
                this.username = user.getUsername();
                this.mobileNumber = user.getMobileNumber();
                this.location = user.getLocation();
                this.gender = user.getGender();
                this.password = user.getPassword();

            }
        }
    private Long id;

    private String username;

    private String password;

    private Long mobileNumber;

    private String location;

    private String gender;

}
