package com.example.PG.s.Dragons.responses;

import com.example.PG.s.Dragons.entities.User;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Data
public class LoginResponse {
    private LocalDate loginDate;
    private String token;
    private User user;

    public LoginResponse(String token,User user) {
        this.token = token;
        this.user=user;
        loginDate=LocalDate.now();
    }
    public static ResponseEntity<LoginResponse> full(String token,User user, HttpStatus httpStatus){
        LoginResponse loginResponse=new LoginResponse(token,user);
        return new ResponseEntity<>(loginResponse,httpStatus);
    }

}
