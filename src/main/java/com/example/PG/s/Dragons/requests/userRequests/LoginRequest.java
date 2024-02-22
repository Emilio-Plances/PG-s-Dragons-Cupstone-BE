package com.example.PG.s.Dragons.requests.userRequests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Enter a username")
    private String username;
    @NotBlank(message = "Enter a password")
    private String password;
}
