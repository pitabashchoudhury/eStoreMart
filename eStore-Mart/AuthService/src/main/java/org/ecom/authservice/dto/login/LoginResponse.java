package org.ecom.authservice.dto.login;

import lombok.*;
import org.ecom.authservice.model.UserDetail;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginResponse {

    public String token;
    public UserDetail userDetail;
    public String message;

}
