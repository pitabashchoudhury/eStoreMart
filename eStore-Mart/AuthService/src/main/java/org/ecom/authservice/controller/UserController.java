package org.ecom.authservice.controller;

import org.ecom.authservice.dto.CreateUserRequest;
import org.ecom.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping(value = "/create-user",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(
            @RequestBody CreateUserRequest request,
            @RequestHeader(value = "X-User", required = false) String createdBy
    ) {
        userService.createUser(
                request,
                createdBy != null ? createdBy : "system"
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully");
    }
}

