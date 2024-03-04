package com.example.PG.s.Dragons.controllers;

import com.cloudinary.Cloudinary;
import com.example.PG.s.Dragons.entities.Character;
import com.example.PG.s.Dragons.exceptions.BadRequestExceptionHandler;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.requests.characterRequests.CharacterRequest;
import com.example.PG.s.Dragons.responses.DefaultResponse;
import com.example.PG.s.Dragons.services.CharacterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    @Autowired
    private CharacterService characterService;
    @Autowired
    private Cloudinary cloudinary;
    @GetMapping
    public ResponseEntity<DefaultResponse> getAll(Pageable pageable){
        return DefaultResponse.noMessage(characterService.findAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> getById(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.noMessage(characterService.findById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<DefaultResponse> save(@RequestBody @Validated CharacterRequest characterRequest, BindingResult bindingResult) throws BadRequestExceptionHandler, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestExceptionHandler(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.noMessage(characterService.save(characterRequest),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable long id,@RequestBody @Validated CharacterRequest characterRequest, BindingResult bindingResult) throws BadRequestExceptionHandler, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestExceptionHandler(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.noMessage(characterService.update(id,characterRequest),HttpStatus.CREATED);
    }
    @PatchMapping("/{id}/upload")
    public ResponseEntity<DefaultResponse> upload(@PathVariable long id, @RequestParam("upload") MultipartFile file) throws IOException, NotFoundException {
        Character character = characterService.upload(id, (String)cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
        return DefaultResponse.full("Image uploaded", character , HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<DefaultResponse> delete(@PathVariable long id) throws NotFoundException {
        characterService.delete(id);
        return DefaultResponse.noObject("Deleted",HttpStatus.OK);
    }
}
