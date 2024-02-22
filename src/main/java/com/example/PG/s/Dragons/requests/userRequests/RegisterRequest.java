package com.example.PG.s.Dragons.requests.userRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    @Size(min=3,max=15,message="The surname must contain 3 to 15 characters.")
    private String surname;
    @NotBlank(message = "The password cannot be empty")
    @Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$")
    private String password;
}
