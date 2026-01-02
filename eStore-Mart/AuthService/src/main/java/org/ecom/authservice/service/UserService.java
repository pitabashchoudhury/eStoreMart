package org.ecom.authservice.service;

import org.ecom.authservice.dto.CreateUserRequest;
import org.ecom.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(CreateUserRequest request
                          // ,String createdBy
    ) {

        // validate userType
        String userType = request.getUserType().toUpperCase();

        //  Prevent duplicate user
        if (userRepository.existsByUsernameOrEmail(
                request.getUsername(), request.getEmail())) {
            throw new RuntimeException("Username or Email already exists");
        }

        //  Fetch user_type.id
        Integer userTypeId =
                userRepository.findUserTypeId(userType);

        if (userTypeId == null) {
            throw new RuntimeException("Invalid user type: " + userType);
        }

        // hash password
        String hashedPassword =
                passwordEncoder.encode(request.getPassword());

        // Insert user
        userRepository.createUser(
                request.getUsername(),
                hashedPassword,
                request.getEmail(),
                userTypeId,
                request.getUsername()
        );
    }
}
