package com.example.Eyebox.service;
import com.example.Eyebox.entity.EyeBoxRegistrationEntity;
import com.example.Eyebox.exception.*;
import com.example.Eyebox.model.EyeBoxRegistrationModel;
import com.example.Eyebox.repository.EyeBoxRegistrationRepo;
//import com.example.Eyebox.service.validation.MobileValidation;
//import com.example.Eyebox.service.validation.PasswordValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Slf4j
public class EyeBoxRegistrationService {
        @Autowired
        private EyeBoxRegistrationRepo userRepository;
    @Autowired
    private TrippleDes trippleDes;
        public EyeBoxRegistrationEntity getUserByUsername(String username) {
            if (username == null) {
                throw new InvalidUsernameException("username cannot be null");
            }
            return userRepository.findByUsername(username);
        }
    public EyeBoxRegistrationEntity getUserByMobile(Long mobileNumber) {
        if (mobileNumber == null) {
            throw new InvalidMobileException("email cannot be null");
        }
        return userRepository.findByMobile(mobileNumber);
    }
        @Transactional
        public EyeBoxRegistrationEntity registerUserAccount(EyeBoxRegistrationModel registerUserAccountDTO) {
            if (registerUserAccountDTO == null) {
                throw new InvalidUserDataException("User account data cannot be null");
            }
            checkIfUsernameNotUsed(registerUserAccountDTO.getUsername());
            EyeBoxRegistrationEntity user = new EyeBoxRegistrationEntity();
            user.setUsername(registerUserAccountDTO.getUsername());
            user.setPassword(registerUserAccountDTO.getPassword());
            user.setMobileNumber(registerUserAccountDTO.getMobileNumber());
            user.setLocation(registerUserAccountDTO.getLocation());
            user.setGender(registerUserAccountDTO.getGender());
            EyeBoxRegistrationEntity userCreated = userRepository.save(user);

            userCreated = userRepository.save(userCreated);

            log.info(String.format("User %s has been created.", userCreated.getId()));
            return userCreated;
        }

        // check if the username has not been registered
        public void checkIfUsernameNotUsed(String username) {
            EyeBoxRegistrationEntity userByUsername = getUserByUsername(username);
            if (userByUsername != null) {
                String msg = String.format("The username %s it's already in use from another user  = %s",
                        userByUsername.getUsername(), userByUsername.getId());
                log.error(msg);
                throw new InvalidUserDataException(msg);
            }
        }
        public void checkIfMobileNotUsed(Long mobile) {
            EyeBoxRegistrationEntity userByMobile = getUserByMobile(mobile);
            if (userByMobile != null) {
                String msg = String.format("The email %s it's already in use from another user with ID = %s",
                        userByMobile.getMobileNumber(), userByMobile.getId());
                log.error(msg);
                throw new InvalidUserDataException(String.format("This email %s it's already in use.",
                        userByMobile.getMobileNumber()));
            }
        }
        @Transactional
        public EyeBoxRegistrationEntity login(String username, String password) {

            if(username.equals(null) || password.equals((null)))
            {
                throw new InvalidLoginException("Username or Password cannot be null or empty");
            }

            EyeBoxRegistrationEntity user = getUserByUsername(username);
            if (user == null){
                throw new InvalidLoginException("No user found with these details");
            }

            log.info(String.format("Login request from %s", username));
            String decrypt = trippleDes.decrypt(user.getPassword());
            System.out.println(decrypt);
            if(decrypt.equals(password) && username.equals(user.getUsername()))
            {
                userRepository.save(user);
                log.info(String.format("Valid login for %s", username));
            }

            else {
                throw new InvalidLoginException("Invalid username or password");
            }
            return user;

    }
}
