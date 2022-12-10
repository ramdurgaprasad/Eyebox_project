package com.example.Eyebox.repository;


import com.example.Eyebox.entity.EyeBoxProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EyeBoxProfileRepo   extends JpaRepository<EyeBoxProfileEntity, Long> {


}
