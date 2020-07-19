package com.travelplanner.travelplanner_server.restservice;


import com.travelplanner.travelplanner_server.exception.DuplicateUserException;
import com.travelplanner.travelplanner_server.exception.FailedAuthenticationException;
import com.travelplanner.travelplanner_server.model.services.JwtUserDetailsService;
import com.travelplanner.travelplanner_server.model.validator.UserValidator;
import com.travelplanner.travelplanner_server.mongodb.dal.UserDAL;
import com.travelplanner.travelplanner_server.model.User;
import com.travelplanner.travelplanner_server.restservice.config.JwtTokenUtil;
import com.travelplanner.travelplanner_server.restservice.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;

@RestController
public class UserController {
    @Autowired
    private UserDAL userDAL;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

//    @Autowired
//    public UserController(UserDAL userDAL, UserValidator userValidator) {
//        this.userDAL = userDAL;
//        this.userValidator = userValidator;
//    }

    /*
     * Below is three methods for demo purpose.
     */
    @RequestMapping(value="/signup", method= RequestMethod.POST)
    public void createNewUser(@RequestBody SignupRequest signupRequest, BindingResult result, HttpSession session) {
        System.out.println("registering!");
        User user = userDAL.findUserByUsername(signupRequest.getUsername());
        if (user != null) {
            throw new DuplicateUserException(user.getUsername());
        }
//        user = User.builder()
//                .username(signupRequest.getUsername())
//                .password(signupRequest.getPassword())
//                .passwordConfirmation(signupRequest.getPasswordConfirmation())
//                .firstname(signupRequest.getFirstname())
//                .lastname(signupRequest.getLastname())
//                .email(signupRequest.getEmail())
//                .profileUrl(signupRequest.getProfileUrl())
//                .build();
        System.out.println("password: " + signupRequest.getPassword());
        System.out.println("confirmPassword: " + signupRequest.getPasswordConfirmation());
        user = new User(
                signupRequest.getUsername(),
                signupRequest.getPassword(),
                signupRequest.getPasswordConfirmation(),
                signupRequest.getFirstname(),
                signupRequest.getLastname(),
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
            user.setPasswordConfirmation(hashedPw);
            userDAL.createUser(user);
            System.out.println("userCreated!");
        }
    }

    // This is the test part, can delete after production
    // here is the test-controller
    @RequestMapping(value="/needauthentication", method=RequestMethod.GET)
    public String authentication(){
        return "Showing the needed authentication! Since we have the token for user!";
    }
    // here is the test-controller
    @RequestMapping(value="/noneauthentication", method=RequestMethod.GET)
    public String noauthentication(){
        return "No Need authentication works!";
    }

    @RequestMapping(value = "/login", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {

        User user = userDAL.findUserByUsername(jwtRequest.getUsername());
        // Handle case where user is not exist or password is different.
        if (user == null || !BCrypt.checkpw(jwtRequest.getPassword(), user.getPassword())) {
            throw new FailedAuthenticationException();
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        // Apply library to generate token...
        System.out.println("token is:" + token);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

