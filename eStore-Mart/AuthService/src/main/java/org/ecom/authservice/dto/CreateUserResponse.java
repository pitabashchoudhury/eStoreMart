package org.ecom.authservice.dto;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {


    private String msg;

    private String userName;
    private String email;

}
