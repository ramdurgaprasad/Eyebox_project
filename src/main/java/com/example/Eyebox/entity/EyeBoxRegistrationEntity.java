package com.example.Eyebox.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
    @Table(name = "signup")
public class EyeBoxRegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_Name", nullable = false)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="mobile_Number", nullable = false)
    private Long mobileNumber;

    @Column(name="location", nullable = false)
    private String location;

    @Column(name = "gender")
    private String gender;

}
