package com.example.PG.s.Dragons.services;

import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.repositories.UserRepository;
import com.example.PG.s.Dragons.requests.userRequests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(RegisterRequest registerRequest){
        User user=new User();
        user.setPassword(registerRequest.getPassword());
        return userRepository.save(userClone(registerRequest,user));
    }

    public User findByUsername(String username) throws NotFoundException {
        Optional<User> optionalUser=userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new NotFoundException("User not found");
        return optionalUser.get();
    }
    private User userClone(RegisterRequest registerRequest, User user){
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setUsername(registerRequest.getUsername());
        return user;
    }
}
