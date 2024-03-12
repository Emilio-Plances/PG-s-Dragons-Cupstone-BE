package com.example.PG.s.Dragons.controllers;

import com.cloudinary.Cloudinary;
import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.exceptions.BadRequestExceptionHandler;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class UserController{
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSenderImpl jms;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtTools jwtTools;
    @PostMapping("/auth/register")
    public ResponseEntity<DefaultResponse> register(@RequestBody @Validated RegisterRequest registerRequest, BindingResult bindingResult) throws BadRequestExceptionHandler {
        if(bindingResult.hasErrors())
            throw new BadRequestExceptionHandler(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        sendEmail(registerRequest);
        return DefaultResponse.full("Success!",userService.register(registerRequest), HttpStatus.CREATED);
    }
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult) throws BadRequestExceptionHandler, NotFoundException, UnauthorizedException {
        if(bindingResult.hasErrors())
            throw new BadRequestExceptionHandler(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        User user=null;
        if(loginRequest.getUsername()==null&&loginRequest.getEmail()==null) throw new BadRequestExceptionHandler("Missing username/Password");
        if(loginRequest.getUsername()!=null) user=userService.findByUsername(loginRequest.getUsername());
        if(loginRequest.getEmail()!=null && user==null) user=userService.findByEmail(loginRequest.getEmail());

        if (!encoder.matches(loginRequest.getPassword(),user.getPassword())) throw new UnauthorizedException("Wrong username/password");
        return LoginResponse.full(jwtTools.createToken(user),user,HttpStatus.OK);
    }
    @GetMapping("/auth/checkUsername")
    public ResponseEntity<DefaultResponse> checkUsername(@RequestParam String username){
        try{
            userService.findByUsername(username);
            return DefaultResponse.noObject("Exist",HttpStatus.OK);
        } catch (NotFoundException e) {
            return DefaultResponse.noObject("Avaiable",HttpStatus.OK);
        }
    }
    @GetMapping("/auth/checkEmail")
    public ResponseEntity<DefaultResponse> checkEmail(@RequestParam String email){
        try{
            userService.findByEmail(email);
            return DefaultResponse.noObject("Exist",HttpStatus.OK);
        } catch (NotFoundException e) {
            return DefaultResponse.noObject("Avaiable",HttpStatus.OK);
        }
    }
    @PatchMapping("/users/{id}")
    public ResponseEntity<DefaultResponse> changeInfo(@PathVariable long id, @RequestBody @Validated UserPatchRequest patchRequest,BindingResult bindingResult) throws BadRequestExceptionHandler, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestExceptionHandler(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.full("Success!",userService.update(id,patchRequest),HttpStatus.OK);
    }
    @PatchMapping("/users/{id}/password")
    public ResponseEntity<DefaultResponse> changePassword(@PathVariable long id, @RequestBody @Validated ChangePassRequest passRequest,BindingResult bindingResult) throws NotFoundException, UnauthorizedException, BadRequestExceptionHandler {
        if(bindingResult.hasErrors())
            throw new BadRequestExceptionHandler(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        User user=userService.findById(id);
        if(!encoder.matches(passRequest.getOldPassword(),user.getPassword())) throw new UnauthorizedException("Passwords must match");
        userService.setNewPassword(id, passRequest.getNewPassword());
        return DefaultResponse.noObject("Password changed",HttpStatus.OK);
    }
    @PatchMapping("/users/{id}/upload")
    public ResponseEntity<DefaultResponse> uploadImage(@PathVariable long id, @RequestParam("upload") MultipartFile file) throws IOException, NotFoundException {
        User user = userService.upload(id, (String)cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
        return DefaultResponse.full("Image uploaded", user, HttpStatus.OK);
    }
    @GetMapping("/noAuth/users")
    public ResponseEntity<DefaultResponse> getAll(Pageable pageable){
        return DefaultResponse.noMessage(userService.findAll(pageable),HttpStatus.OK);
    }
    @GetMapping("/noAuth/users/{id}")
    public ResponseEntity<DefaultResponse> getUserById(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.noMessage(userService.findById(id),HttpStatus.OK);
    }
    @GetMapping("/noAuth/users/param")
    public ResponseEntity<DefaultResponse> getUserByUsername(@RequestParam String username) throws NotFoundException {
        return DefaultResponse.noMessage(userService.findByUsername(username),HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable long id) throws NotFoundException {
        userService.delete(id);
        return DefaultResponse.noObject("User deleted!",HttpStatus.OK);
    }
    private void sendEmail(RegisterRequest user){
        SimpleMailMessage mail=new SimpleMailMessage();
        String message="Dear "+user.getName()+" "+ user.getSurname()+",\n" +
                "\n" +
                "Thank you for choosing to subscribe to PgsAndDragon. We are excited to have you as a new member of our community and are here to provide you with an extraordinary experience.\n" +
                "\n" +
                "Thank you again for choosing to be part of our community. We look forward to providing you with an extraordinary experience with PGsAndDragon.\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "Adolf (Master)";
        mail.setTo(user.getEmail());
        mail.setSubject("Subscription Activation");
        mail.setText(message);
        jms.send(mail);
    }
}
