package com.example.Eyebox.controller;

import com.example.Eyebox.entity.EyeBoxProfileEntity;
import com.example.Eyebox.service.EyeBoxProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EyeBoxProfile {

    @Autowired
    private EyeBoxProfileService service;

    @PostMapping("/profile")
    public String saveProfile(@RequestParam("image") MultipartFile image,
                              @RequestParam("name") String name,
                              @RequestParam("gmail") String gmail,
                              @RequestParam("mobile") String mobile,
                              @RequestParam("location") String location,
                              @RequestParam("gender") String gender)
    {
        String response=service.saveProfile(image, name, gmail, mobile,location,gender);
        return response;
    }


    @GetMapping("/get/{id}")
    public EyeBoxProfileEntity getProfile(@PathVariable Long id) {

       EyeBoxProfileEntity response= service.getProfile(id);

       return response;
    }

    @PutMapping("/edit/{id}")
    public EyeBoxProfileEntity editProfile(@PathVariable Long id,
            @RequestParam("image") MultipartFile image,
                              @RequestParam("name") String name,
                              @RequestParam("gmail") String gmail,
                              @RequestParam("mobile") String mobile,
                              @RequestParam("location") String location,
                              @RequestParam("gender") String gender)
    {
        EyeBoxProfileEntity response=service.editProfile(id,image, name, gmail, mobile,location,gender);
        return response;
    }


}
