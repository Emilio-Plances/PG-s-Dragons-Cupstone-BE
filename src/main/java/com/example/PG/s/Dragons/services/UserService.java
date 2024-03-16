package com.example.PG.s.Dragons.services;

import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.enums.Role;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.repositories.UserRepository;
import com.example.PG.s.Dragons.requests.userRequests.UserPatchRequest;
import com.example.PG.s.Dragons.requests.userRequests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findById(long id) throws NotFoundException {
        Optional<User> optionalUser=userRepository.findById(id);
        if(optionalUser.isEmpty()) throw new NotFoundException("User not found");
        return optionalUser.get();
    }
    public User findByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new NotFoundException("User not found"));
    }
    public User findByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("User not found"));
    }
    public User register(RegisterRequest registerRequest){
        User user=new User();
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setEmail(registerRequest.getEmail().toLowerCase());
        user.setUsername(registerRequest.getUsername());
        user.setPublicUsername(registerRequest.getUsername());
        user.setBirthday(registerRequest.getBirthday());
        user.setRole(Role.User);
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        return userRepository.save(user);
    }
    public User update(long id, UserPatchRequest patchRequest) throws NotFoundException {
        User user=findById(id);
        if(patchRequest.getName()!=null) user.setName(patchRequest.getName());
        if(patchRequest.getSurname()!=null)user.setSurname(patchRequest.getSurname());
        if(patchRequest.getEmail()!=null) user.setEmail(patchRequest.getEmail().toLowerCase());
        if(patchRequest.getInfo()!=null) user.setInfo(patchRequest.getInfo());
        if(patchRequest.getPublicUsername()!=null) user.setPublicUsername(patchRequest.getPublicUsername());
        if(patchRequest.getBirthday()!=null) user.setBirthday(patchRequest.getBirthday());
        return userRepository.save(user);
    }
    public void setNewPassword(long id,String newPassword) throws NotFoundException {
        User user=findById(id);
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }
    public void delete(long id) throws NotFoundException {
        User user=findById(id);
        userRepository.delete(user);
    }
    public User upload(long id,String link) throws NotFoundException {
        User user=findById(id);
        user.setLinkPhoto(link);
        return userRepository.save(user);
    }
    public List<User> searchByPublicUsername(String publicUsername){
        return userRepository.searchByPublicUsername(publicUsername);
    }
}