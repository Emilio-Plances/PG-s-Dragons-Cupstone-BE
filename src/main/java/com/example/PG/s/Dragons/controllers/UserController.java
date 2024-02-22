package com.example.PG.s.Dragons.controllers;

import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.exceptions.BadRequestException;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.exceptions.UnauthorizedException;
import com.example.PG.s.Dragons.requests.userRequests.LoginRequest;
import com.example.PG.s.Dragons.requests.userRequests.RegisterRequest;
import com.example.PG.s.Dragons.responses.DefaultResponse;
import com.example.PG.s.Dragons.responses.LoginResponse;
import com.example.PG.s.Dragons.security.JwtTools;
import com.example.PG.s.Dragons.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController{
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTools jwtTools;

    @PostMapping("/auth/register")
    public ResponseEntity<DefaultResponse> register(@RequestBody @Validated RegisterRequest registerRequest, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.full("Success!",registerRequest, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult) throws BadRequestException, NotFoundException, UnauthorizedException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        User user=userService.findByUsername(loginRequest.getUsername());
        if (!encoder.matches(loginRequest.getPassword(),user.getPassword())) throw new UnauthorizedException("Wrong username/password");
        return LoginResponse.full(jwtTools.createToken(user),user,HttpStatus.OK);
    }
}
