package com.example.PG.s.Dragons.requests.userRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "The username cannot be empty")
    @Size(min=3,max=15,message="The username must contain 3 to 15 characters.")
    private String username;
    @NotBlank(message = "The name cannot be empty")
    @Size(min=3,max=15,message="The name must contain 3 to 15 characters.")
    private String name;
    @NotBlank(message = "The surname cannot be empty")
    @Size(min=3,max=15,message="The surname must contain 3 to 15 characters.")
    private String surname;
    private String password;
}
