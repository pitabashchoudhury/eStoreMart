package org.ecom.authservice.service;

import org.ecom.authservice.dto.CreateUserRequest;
import org.ecom.authservice.dto.login.LoginResponse;
import org.ecom.authservice.dto.login.LoginUserRequest;
import org.ecom.authservice.model.UserDetail;
import org.ecom.authservice.repository.UserRepository;
import org.ecom.authservice.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
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


    public LoginResponse loginUser(LoginUserRequest loginUserRequest) {

        Optional<UserDetail> ss = userRepository.getUserDetail(loginUserRequest.getUserEmail());

        if (ss.isEmpty()) {
            throw new RuntimeException(
                    "User not found"
            );
        } else {


            if (!ss.get().getIsActive()) {
                throw new RuntimeException("User not Active");
            }

            String decodedPassword = passwordEncoder.encode(loginUserRequest.getPassword());

            if (ss.get().getPassword().equals(decodedPassword)) {


                String token = jwtUtils.generateJWT(ss.get());
                return new LoginResponse(
                        token,
                        ss.get(),
                        "Login done Successfully!!!"
                );


            } else {
                throw new RuntimeException(
                        "Password not matched"
                );
            }
        }


    }
}
