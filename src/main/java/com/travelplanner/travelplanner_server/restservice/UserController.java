package com.travelplanner.travelplanner_server.restservice;


import com.travelplanner.travelplanner_server.exception.DuplicateUserException;
import com.travelplanner.travelplanner_server.exception.FailedAuthenticationException;
import com.travelplanner.travelplanner_server.model.validator.UserValidator;
import com.travelplanner.travelplanner_server.mongodb.dal.UserDAL;
import com.travelplanner.travelplanner_server.model.User;
import com.travelplanner.travelplanner_server.restservice.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;

@RestController
public class UserController {

    private final UserDAL userDAL;
    // Validator
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserDAL userDAL, UserValidator userValidator) {
        this.userDAL = userDAL;
        this.userValidator = userValidator;
    }

    /*
     * Below is three methods for demo purpose.
     */
    @RequestMapping(value="/signup", method= RequestMethod.POST)
    public void createNewUser(@RequestBody SignupRequest signupRequest, BindingResult result, HttpSession session) {
        System.out.println("registering!");
        User user = userDAL.findUserByUsername(signupRequest.getUserName());
        if (user != null) {
            throw new DuplicateUserException(user.getUsername());
        }
//        user = User.builder()
//                .username(signupRequest.getUserName())
//                .password(signupRequest.getPassWord())
//                .passwordConfirmation(signupRequest.getPasswordConfirmation())
//                .firstname(signupRequest.getFirstName())
//                .lastname(signupRequest.getLastName())
//                .email(signupRequest.getEmail())
//                .profileUrl(signupRequest.getProfileUrl())
//                .build();
        user = new User(
                signupRequest.getUserName(),
                signupRequest.getPassWord(),
                signupRequest.getPasswordConfirmation(),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getEmail(),
                signupRequest.getProfileUrl()
                );
        userValidator.validate(user, result);
        if(result.hasErrors()){
            System.out.println("Two password not match!");
            for(ObjectError error: result.getAllErrors()){
                System.out.println("error is: " + error.toString());
            }
            List<ObjectError> errors = result.getAllErrors();
            session.setAttribute("errors", errors);
        }else {
            String hashedPw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            System.out.println("pwd: " + user.getPassword() + " hashedPwd: " + hashedPw);
            user.setPassword(hashedPw);
            userDAL.createUser(user);
            System.out.println("userCreated!");
        }
    }

    // here is the test-controller
    @RequestMapping(value="/authentication", method=RequestMethod.GET)
    public String authentication(){
        return "Showing the needed authentication!";
    }
    // here is the test-controller
    @RequestMapping(value="/noneauthentication", method=RequestMethod.GET)
    public String noauthentication(){
        return "No Need authentication!";
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {

        User user = userDAL.findUserByUsername(jwtRequest.getUsername());
        // Handle case where user is not exist or password is different.
        if (user == null || !BCrypt.checkpw(jwtRequest.getPassword(), user.getPassword())) {
            throw new FailedAuthenticationException();
        }
        // Apply library to generate token...

        return ResponseEntity.ok().body(new JwtResponse("Example token"));
    }
}

