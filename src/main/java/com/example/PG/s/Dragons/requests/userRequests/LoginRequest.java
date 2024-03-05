package com.example.PG.s.Dragons.requests.userRequests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String email;
    @NotBlank(message = "Enter a password")
    private String password;
}
