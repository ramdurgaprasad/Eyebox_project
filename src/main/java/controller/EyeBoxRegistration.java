package controller;

import model.EyeBoxRegistrationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.EyeBoxRegistrationService;

@RestController
@RequestMapping(value = "/users")
public class EyeBoxRegistration {


    @Autowired
    private EyeBoxRegistrationService userService;

    // register a new user's account: no all the user information are required
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerNewUserAccount(@RequestBody EyeBoxRegistrationModel eyeBoxRegistrationModel) {
        return new ResponseEntity(new UserDTO(userService.registerUserAccount(registerUserAccountDTO)), null, HttpStatus.CREATED);
    }

}
