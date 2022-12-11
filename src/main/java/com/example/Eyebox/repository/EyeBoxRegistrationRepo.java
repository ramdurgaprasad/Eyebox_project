package com.example.Eyebox.repository;

import com.example.Eyebox.entity.EyeBoxRegistrationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EyeBoxRegistrationRepo extends CrudRepository<EyeBoxRegistrationEntity,Long> {
    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    EyeBoxRegistrationEntity findByMobile(@Param("email") Long mobileNumber);
    @Query(value = "SELECT u FROM User u WHERE u.username = :username")
    EyeBoxRegistrationEntity findByUsername(@Param("username")String username);
}
