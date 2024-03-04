package com.example.PG.s.Dragons.services;

import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.repositories.UserRepository;
import com.example.PG.s.Dragons.requests.userRequests.UserPatchRequest;
import com.example.PG.s.Dragons.requests.userRequests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }
    public User findById(long id) throws NotFoundException {
        Optional<User> optionalUser=userRepository.findById(id);
        if(optionalUser.isEmpty()) throw new NotFoundException("User not found");
        return optionalUser.get();
    }
    public User findByUsername(String username) throws NotFoundException {
        Optional<User> optionalUser=userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new NotFoundException("User not found");
        return optionalUser.get();
    }
    public User register(RegisterRequest registerRequest){
        User user=new User();
        String name=registerRequest.getName().toUpperCase().charAt(0)+registerRequest.getName().toLowerCase().substring(1);
        String surname=registerRequest.getSurname().toUpperCase().charAt(0)+registerRequest.getSurname().toLowerCase().substring(1);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(registerRequest.getEmail().toLowerCase());
        user.setUsername(registerRequest.getUsername());
        user.setBirthday(registerRequest.getBirthday());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        return userRepository.save(user);
    }
    public User update(long id, UserPatchRequest patchRequest) throws NotFoundException {
        User user=findById(id);

        if(patchRequest.getName()!=null){
            String name=patchRequest.getName().toUpperCase().charAt(0)+patchRequest.getName().toLowerCase().substring(1);
            user.setName(name);
        }
        if(patchRequest.getSurname()!=null){
            user.setSurname(patchRequest.getSurname());
            String surname=patchRequest.getSurname().toUpperCase().charAt(0)+patchRequest.getSurname().toLowerCase().substring(1);
        }
        if(patchRequest.getEmail()!=null) user.setEmail(patchRequest.getEmail().toLowerCase());
        if(patchRequest.getInfo()!=null) user.setInfo(patchRequest.getInfo());
        if(patchRequest.getUsername()!=null) user.setUsername(patchRequest.getUsername());
        if(patchRequest.getBirthday()!=null) user.setBirthday(patchRequest.getBirthday());
        return userRepository.save(user);
    }
    public void setNewPassword(long id,String newPassword) throws NotFoundException {
        User user=findById(id);
        user.setPassword(encoder.encode(newPassword));
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
}