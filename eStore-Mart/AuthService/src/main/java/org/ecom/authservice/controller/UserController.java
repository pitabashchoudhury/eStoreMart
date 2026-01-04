package org.ecom.authservice.controller;

import org.ecom.authservice.dto.CreateUserRequest;
import org.ecom.authservice.dto.login.LoginResponse;
import org.ecom.authservice.dto.login.LoginUserRequest;
import org.ecom.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(
            @RequestBody CreateUserRequest request
            //,
            //@RequestHeader(value = "X-User", required = false) String createdBy
    ) {
        userService.createUser(
                request
                //,createdBy != null ? createdBy : "system"
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully");
    }


    @PostMapping(value = "/login-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> loginUser (@RequestBody LoginUserRequest loginUserRequest){

        LoginResponse loginResponse= userService.loginUser(loginUserRequest);

        return  ResponseEntity.status(HttpStatusCode.valueOf(200)).body(loginResponse);
    }
}

