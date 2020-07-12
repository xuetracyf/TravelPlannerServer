package com.travelplanner.travelplanner_server.restservice;


import com.travelplanner.travelplanner_server.exception.DuplicateUserException;
import com.travelplanner.travelplanner_server.exception.FailedAuthenticationException;
import com.travelplanner.travelplanner_server.mongodb.dal.UserDAL;
import com.travelplanner.travelplanner_server.model.User;
import com.travelplanner.travelplanner_server.restservice.payload.LoginRequest;
import com.travelplanner.travelplanner_server.restservice.payload.SignupRequest;
import com.travelplanner.travelplanner_server.restservice.payload.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    private final UserDAL userDAL;

    @Autowired
    public UserController(UserDAL userDAL) {
        this.userDAL = userDAL;
    }

    /*
     * Below is three methods for demo purpose.
     */
    @PostMapping(value = "/signup")
    public void createNewUser(@RequestBody SignupRequest signupRequest) {
        User user = userDAL.findUserByUsername(signupRequest.getUsername());
        if (user != null) {
            throw new DuplicateUserException(user.getUsername());
        }
        user = User.builder()
                .username(signupRequest.getUsername())
                .password(signupRequest.getPassword())
                .first_name(signupRequest.getFirst_name())
                .last_name(signupRequest.getLast_name())
                .build();

        userDAL.createUser(user);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        User user = userDAL.findUserByUsername(loginRequest.getUsername());
        // Handle case where user is not exist or password is different.
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            throw new FailedAuthenticationException();
        }
        // Apply library to generate token...
        return ResponseEntity.ok().body(new LoginResponse("Example token"));
    }
}
