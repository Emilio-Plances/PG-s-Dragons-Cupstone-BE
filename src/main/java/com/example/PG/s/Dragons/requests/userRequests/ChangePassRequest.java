package com.example.PG.s.Dragons.requests.userRequests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePassRequest {
    @NotBlank(message = "Enter old password")
    private String oldPassword;
    @NotBlank(message = "Enter new password")
    private String newPassword;
}
