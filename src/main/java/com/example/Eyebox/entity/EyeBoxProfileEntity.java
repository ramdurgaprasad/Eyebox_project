package com.example.Eyebox.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "profile")
public class EyeBoxProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    private String name;
    private String gmail;
    private String mobile;
    private String location;
    private String gender;
    private String image;

}
