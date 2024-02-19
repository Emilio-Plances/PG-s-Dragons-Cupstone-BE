package com.example.PG.s.Dragons.responses;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ErrorResponse {
    private String errorMessage;
    private LocalDateTime errorDate;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        errorDate=LocalDateTime.now();
    }
}
