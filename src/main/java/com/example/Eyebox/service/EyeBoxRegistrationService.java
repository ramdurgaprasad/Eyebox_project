package com.example.Eyebox.service;

import com.example.Eyebox.entity.EyeBoxRegistrationEntity;
import com.example.Eyebox.exception.*;
import com.example.Eyebox.model.EyeBoxRegistrationModel;
import com.example.Eyebox.repository.EyeBoxRegistrationRepo;
import com.example.Eyebox.service.validation.MobileValidation;
import com.example.Eyebox.service.validation.PasswordValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class EyeBoxRegistrationService {
        @Autowired
        private EyeBoxRegistrationRepo userRepository;
    @Autowired
    private TrippleDes trippleDes;
        @Value("${microservice.security.salt}")
        private String salt;

        private PasswordValidator passwordValidator;
        private MobileValidation mobileValidation;

        public EyeBoxRegistrationService() {
            passwordValidator = new PasswordValidator();
            mobileValidation = new MobileValidation();
        }

        public ArrayList<EyeBoxRegistrationModel> getUserPresentationList() {
            ArrayList<EyeBoxRegistrationModel> listDto = new ArrayList<>();
            Iterable<EyeBoxRegistrationEntity> list = getUserList();
            list.forEach(e -> listDto.add(new EyeBoxRegistrationModel(e)));
            return listDto;
        }

        public EyeBoxRegistrationEntity getUserById(Long id) {
            if (id == null) {
                throw new InvalidUserIdentifierException("User Id cannot be null");
            }
            Optional<EyeBoxRegistrationEntity> userOpt = userRepository.findById(id);
            if (userOpt.isPresent()) {
                return userOpt.get();
            }
            throw new UserNotFoundException(String.format("User not found for Id = %s", id));
        }

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
            passwordValidator.checkPassword(registerUserAccountDTO.getPassword());


            // create the new user account: not all the user information required
            EyeBoxRegistrationEntity user = new EyeBoxRegistrationEntity();
            user.setUsername(registerUserAccountDTO.getUsername());
            user.setPassword(EncryptionService.encrypt(registerUserAccountDTO.getPassword(), salt));

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

        // check if the email has not been registered
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
        public EyeBoxRegistrationEntity createUser(EyeBoxRegistrationModel createUserDTO) {
            if (createUserDTO == null) {
                throw new InvalidUserDataException("User account data cannot be null");
            }

            checkIfUsernameNotUsed(createUserDTO.getUsername());
            checkIfMobileNotUsed(createUserDTO.getMobileNumber());
            passwordValidator.checkPassword(createUserDTO.getPassword());
            mobileValidation.checkPhone(createUserDTO.getMobileNumber());

            // create the user
            EyeBoxRegistrationEntity user = new EyeBoxRegistrationEntity();
            user.setUsername(createUserDTO.getUsername());
            user.setPassword(EncryptionService.encrypt(createUserDTO.getPassword(), salt));

            user.setLocation(createUserDTO.getLocation());
            user.setGender(createUserDTO.getGender());

            // date of birth
            user.setMobileNumber(createUserDTO.getMobileNumber());

            EyeBoxRegistrationEntity userCreated = userRepository.save(user);


            userCreated = userRepository.save(userCreated);

            log.info(String.format("User %s has been created.", userCreated.getId()));
            return userCreated;
        }
    @Transactional
    public EyeBoxRegistrationEntity updateUser(Long id, EyeBoxRegistrationModel updateUserDTO) {
        if (id == null) {
            throw new InvalidUserIdentifierException("Id cannot be null");
        }
        if (updateUserDTO == null) {
            throw new InvalidUserDataException("User account data cannot be null");
        }
        Optional<EyeBoxRegistrationEntity> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException(String.format("The user with Id = %s doesn't exists", id));
        }
        EyeBoxRegistrationEntity user = userOpt.get();
        // check if the username has not been registered
        EyeBoxRegistrationEntity userByUsername = getUserByUsername(updateUserDTO.getUsername());
        if (userByUsername != null) {
            // check if the user's id is different than the actual user
            if (!user.getId().equals(userByUsername.getId())) {
                String msg = String.format("The username %s it's already in use from another user with ID = %s",
                        updateUserDTO.getUsername(), userByUsername.getId());
                log.error(msg);
                throw new InvalidUserDataException(msg);
            }
        }

        passwordValidator.checkPassword(updateUserDTO.getPassword());
        mobileValidation.checkPhone(updateUserDTO.getMobileNumber());
        // check if the new email has not been registered yet
        EyeBoxRegistrationEntity users = getUserByUsername(updateUserDTO.getUsername());
        if (users != null) {
            // check if the user's email is different than the actual user
            if (!users.getId().equals(user.getId())) {
                String msg = String.format("The email %s it's already in use from another user with ID = %s",
                        updateUserDTO.getUsername(), users.getId());
                log.error(msg);
                throw new InvalidUserDataException(msg);
            }
        }

        // update the user
        user.setUsername(updateUserDTO.getUsername());

        // using the user's salt to secure the new validated password
        user.setPassword(EncryptionService.encrypt(updateUserDTO.getPassword(), salt));
        user.setMobileNumber(updateUserDTO.getMobileNumber());
        user.setUsername(updateUserDTO.getUsername());

        user.setLocation(updateUserDTO.getLocation());

        user.setGender(updateUserDTO.getGender());
        // set contact: entity always present
        EyeBoxRegistrationEntity userUpdated = userRepository.save(user);
        log.info(String.format("User %s has been updated.", user.getId()));

        return userUpdated;
    }


        public Iterable<EyeBoxRegistrationEntity> getUserList() {
            return userRepository.findAll();
        }
        @Transactional
        public EyeBoxRegistrationEntity login(String username, String password) {
//        if ((Strings.isNullOrEmpty(email)) || (Strings.isNullOrEmpty(password))) {
//            throw new InvalidLoginException("Username or Password cannot be null or empty");
//        }

            if(username.equals(null) || password.equals((null)))
            {
                throw new InvalidLoginException("Username or Password cannot be null or empty");
            }

            EyeBoxRegistrationEntity user = getUserByUsername(username);
            if (user == null){
                throw new InvalidLoginException("No user found with these details");
            }

            log.info(String.format("Login request from %s", username));

            // check the password
            //if (EncryptionService.isPasswordValid(password, user.getPassword(), salt)) {
            // check if the user is enabled

            String decrypt = trippleDes.decrypt(user.getPassword());
            System.out.println(decrypt);
            if(decrypt.equals(password) && username.equals(user.getUsername()))
            {
                userRepository.save(user);
                log.info(String.format("Valid login for %s", username));
            }

            else {
                throw new InvalidLoginException("Invalid email or password");
            }
            return user;

    }
}
