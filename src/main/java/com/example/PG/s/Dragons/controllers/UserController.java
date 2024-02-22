package com.example.PG.s.Dragons.controllers;

import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.exceptions.BadRequestException;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.exceptions.UnauthorizedException;
import com.example.PG.s.Dragons.requests.userRequests.ChangePassRequest;
import com.example.PG.s.Dragons.requests.userRequests.LoginRequest;
import com.example.PG.s.Dragons.requests.userRequests.UserPatchRequest;
import com.example.PG.s.Dragons.requests.userRequests.RegisterRequest;
import com.example.PG.s.Dragons.responses.DefaultResponse;
import com.example.PG.s.Dragons.responses.LoginResponse;
import com.example.PG.s.Dragons.security.JwtTools;
import com.example.PG.s.Dragons.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("/users/{id}/password")
    public ResponseEntity<DefaultResponse> changePassword(@PathVariable long id, @RequestBody @Validated ChangePassRequest passRequest,BindingResult bindingResult) throws NotFoundException, UnauthorizedException, BadRequestException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        User user=userService.findById(id);
        if(!encoder.matches(passRequest.getOldPassword(),user.getPassword())) throw new UnauthorizedException("Passwords must match");
        userService.setNewPassword(id, passRequest.getNewPassword());
        return DefaultResponse.noObject("Password changed",HttpStatus.OK);
    }
    @PatchMapping("/users/{id}")
    public ResponseEntity<DefaultResponse> changeInfo(@PathVariable long id, @RequestBody @Validated UserPatchRequest patchRequest,BindingResult bindingResult) throws BadRequestException, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.full("Success!",userService.update(id,patchRequest),HttpStatus.OK);
    }
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DefaultResponse> getAll(Pageable pageable){
        return DefaultResponse.noMessage(userService.findAll(pageable),HttpStatus.OK);
    }
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DefaultResponse> getUserById(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.noMessage(userService.findById(id),HttpStatus.OK);
    }
    @GetMapping("/users/{username}")
    public ResponseEntity<DefaultResponse> getUserByUsername(@PathVariable String username) throws NotFoundException {
        return DefaultResponse.noMessage(userService.findByUsername(username),HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable long id) throws NotFoundException {
        userService.delete(id);
        return DefaultResponse.noObject("User deleted!",HttpStatus.OK);
    }
}
