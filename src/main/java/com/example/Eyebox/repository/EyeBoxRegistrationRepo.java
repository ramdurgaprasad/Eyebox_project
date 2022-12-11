package com.example.Eyebox.repository;

import com.example.Eyebox.entity.EyeBoxRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EyeBoxRegistrationRepo extends JpaRepository<EyeBoxRegistrationEntity,Long> {

    EyeBoxRegistrationEntity findByUsername(String username);
}
