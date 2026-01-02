package org.ecom.authservice.dto.login;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginResponse {

    public String token;

    public String message;
}
