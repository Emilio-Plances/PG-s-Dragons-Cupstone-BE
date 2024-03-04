package com.example.PG.s.Dragons.requests.userRequests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserPatchRequest {
    @Pattern(regexp = ".*[^ ].*",message = "Enter a valid username!")
    @Size(min=3,max=15,message="The name must contain 3 to 15 characters.")
    private String username;
    @Pattern(regexp = ".*[^ ].*",message = "Enter a valid name!")
    @Size(min=3,max=15,message="The name must contain 3 to 15 characters.")
    private String name;
    @Pattern(regexp = ".*[^ ].*",message = "Enter a valid surname!")
    @Size(min=3,max=15,message="The name must contain 3 to 15 characters.")
    private String surname;
    private LocalDate birthday;
    @Pattern(regexp = ".*[^ ].*",message = "Enter a valid info")
    @Max(255)
    private String info;
    @Email
    private String email;
}
