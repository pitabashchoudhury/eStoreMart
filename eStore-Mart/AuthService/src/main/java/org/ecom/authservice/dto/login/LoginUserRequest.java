package org.ecom.authservice.dto.login;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {

    private String userEmail;
    private String password;
}
