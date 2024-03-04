package com.example.PG.s.Dragons.requests.userRequests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    @NotBlank(message = "The username cannot be empty")
    @Size(min=3,max=15,message="The username must contain 3 to 15 characters.")
    private String username;
    @NotBlank(message = "The name cannot be empty")
    @Size(min=3,max=15,message="The name must contain 3 to 15 characters.")
    private String name;
    @NotBlank
    @Size(min=3,max=15,message="The surname must contain 3 to 15 characters.")
    private String surname;
    private LocalDate birthday;
    @NotBlank(message = "The email cannot be empty")
    @Email(message = "Enter a valid email")
    private String email;
    @Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>.]).{8,}$",
            message = "Password must contain: -1 letter uppercase -1 letter lowercase -1 number 1 special character -Min 8 char")
    private String password;
}
