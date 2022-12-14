package com.example.Eyebox.service;
import com.example.Eyebox.entity.EyeBoxRegistrationEntity;
import com.example.Eyebox.exception.*;
import com.example.Eyebox.model.EyeBoxForgotPasswordModel;
import com.example.Eyebox.model.EyeBoxRegistrationModel;
import com.example.Eyebox.model.ResetPassword;
import com.example.Eyebox.repository.EyeBoxRegistrationRepo;
//import com.example.Eyebox.service.validation.MobileValidation;
//import com.example.Eyebox.service.validation.PasswordValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Slf4j
public class EyeBoxRegistrationService {
        @Autowired
        private EyeBoxRegistrationRepo userRepository;
/*    @Autowired
    private TrippleDes trippleDes;
        public EyeBoxRegistrationEntity getUserByUsername(String username) {
            if (username == null) {
                throw new InvalidUsernameException("username cannot be null");
            }
            return userRepository.findByUsername(username);
        }*/



   /* public EyeBoxRegistrationEntity getUserByMobile(Long mobileNumber) {
        if (mobileNumber == null) {
            throw new InvalidMobileException("email cannot be null");
        }
        return userRepository.findByMobile(mobileNumber);
    }*/
        @Transactional
        public EyeBoxRegistrationEntity registerUserAccount(EyeBoxRegistrationModel registerUserAccountDTO) {
            if (registerUserAccountDTO == null) {
                throw new InvalidUserDataException("User account data cannot be null");
            }

           // checkIfUsernameNotUsed(registerUserAccountDTO.getUsername());
            EyeBoxRegistrationEntity user = new EyeBoxRegistrationEntity();
            user.setUsername(registerUserAccountDTO.getUsername());
            user.setPassword(registerUserAccountDTO.getPassword());
            user.setMobileNumber(registerUserAccountDTO.getMobileNumber());
            user.setLocation(registerUserAccountDTO.getLocation());
            user.setGender(registerUserAccountDTO.getGender());

            return  userRepository.save(user);
        }

        // check if the username has not been registered
      /*  public void checkIfUsernameNotUsed(String username) {
            EyeBoxRegistrationEntity userByUsername = getUserByUsername(username);
            if (userByUsername != null) {
                String msg = String.format("The username %s it's already in use from another user  = %s",
                        userByUsername.getUsername(), userByUsername.getId());
                log.error(msg);
                throw new InvalidUserDataException(msg);
            }
        }*/
     /*   public void checkIfMobileNotUsed(Long mobile) {
            EyeBoxRegistrationEntity userByMobile = getUserByMobile(mobile);
            if (userByMobile != null) {
                String msg = String.format("The email %s it's already in use from another user with ID = %s",
                        userByMobile.getMobileNumber(), userByMobile.getId());
                log.error(msg);
                throw new InvalidUserDataException(String.format("This email %s it's already in use.",
                        userByMobile.getMobileNumber()));
            }
        }*/


        public EyeBoxRegistrationEntity login(String username, String password) {

            if(username.equals(null) || password.equals((null)))
            {
                throw new InvalidLoginException("Username or Password cannot be null or empty");
            }

            EyeBoxRegistrationEntity user = userRepository.findByUsername(username);
            if (user == null){
                throw new InvalidLoginException("No user found with these details");
            }


            if(user.getPassword().equals(password) && username.equals(user.getUsername()))
            {
                userRepository.save(user);
            }

            else {
                throw new InvalidLoginException("Invalid username or password");
            }

            return user;
    }

    public String forgot(EyeBoxForgotPasswordModel passwordModel, Long id) {

        String res="successful";

        if(id==null || passwordModel==null ) {
            res="unsuccessful";
        }

        EyeBoxRegistrationEntity user = userRepository.findById(id).get();

        if(user.getPassword().equals(passwordModel.getOldPassword())  ) {
            user.setPassword(passwordModel.getNewPassword());
            userRepository.save(user);
        }else {
            res="unsuccessful";
        }

       return res;
    }

    public String resetPassword(ResetPassword password, Long id) {

        String res="successful";
            if(password.getNewPassword().isEmpty() && id!=null  ) {

                res="unsuccessful";

            }else {
                EyeBoxRegistrationEntity user = userRepository.findById(id).get();

                user.setPassword(password.getNewPassword());

                userRepository.save(user);
            }
            return res;
    }

    public List<EyeBoxRegistrationEntity> getUser() {

         return  userRepository.findAll();

    }
}
