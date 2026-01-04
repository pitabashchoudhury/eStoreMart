package org.ecom.authservice.model;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {

    private String userName;
    private String email;
    private Boolean isActive;
    private String userType;
    private Long id;
    private String password;
}
