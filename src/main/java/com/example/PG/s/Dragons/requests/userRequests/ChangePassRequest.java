package com.example.PG.s.Dragons.requests.userRequests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePassRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
