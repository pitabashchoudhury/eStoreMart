package org.ecom.authservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CreateUserRequest {

    String username;
    String password;
    String email;
    String userType;
}
