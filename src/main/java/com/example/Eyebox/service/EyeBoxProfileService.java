package com.example.Eyebox.service;

import com.example.Eyebox.entity.EyeBoxProfileEntity;
import com.example.Eyebox.repository.EyeBoxProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;


@Service
public class EyeBoxProfileService {
    @Autowired
    private EyeBoxProfileRepo repo;

    public String saveProfile(MultipartFile image, String name, String gmail, String mobile, String location, String gender) {

        String res="not success";
        EyeBoxProfileEntity profileEntity = new EyeBoxProfileEntity();
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            profileEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileEntity.setName(name);
        profileEntity.setGender(gender);
        profileEntity.setGmail(gmail);
        profileEntity.setMobile(mobile);
        profileEntity.setLocation(location);

        if (profileEntity != null) {
            res="success";
            repo.save(profileEntity);

        }
        return res;
    }


    public EyeBoxProfileEntity getProfile(Long id) {
       return repo.findById(id).get();
    }


    public EyeBoxProfileEntity editProfile(Long id,MultipartFile image, String name, String gmail, String mobile, String location, String gender) {


        EyeBoxProfileEntity oldEntity=getProfile(id);


        if(image!=null) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            if (fileName.contains("..")) {
                System.out.println("not a a valid file");
            }
            try {
                oldEntity.setImage(image.isEmpty() ? oldEntity.getImage() : Base64.getEncoder().encodeToString(image.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        oldEntity.setName(name.isEmpty() ?  oldEntity.getName(): name);
        oldEntity.setGmail(gmail.isEmpty() ?  oldEntity.getGmail() : gmail);
        oldEntity.setMobile(mobile.isEmpty() ?  oldEntity.getMobile() : mobile);
        oldEntity.setLocation(location.isEmpty() ?  oldEntity.getLocation() : location);
        oldEntity.setGender(gender.isEmpty() ?  oldEntity.getGender() : gender);

        repo.save(oldEntity);

       return  oldEntity;
    }
}
