package org.ecom.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        System.out.println("Wel Come To Auth Service");
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
