package com.example.PG.s.Dragons.controllers;

import com.cloudinary.Cloudinary;
import com.example.PG.s.Dragons.entities.Character;
import com.example.PG.s.Dragons.enums.PgClass;
import com.example.PG.s.Dragons.enums.Race;
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
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CharacterController {
    @Autowired
    private CharacterService characterService;
    @Autowired
    private Cloudinary cloudinary;
    @GetMapping("/noAuth/characters")
    public ResponseEntity<DefaultResponse> getAll(){
        return DefaultResponse.noMessage(characterService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/noAuth/characters/{id}")
    public ResponseEntity<DefaultResponse> getById(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.noMessage(characterService.findById(id),HttpStatus.OK);
    }
    @GetMapping("/noAuth/characters/query")
    public ResponseEntity<DefaultResponse> filter(@RequestParam Optional<PgClass> optionalPgClass, @RequestParam Optional<Race> optionalRace){
        if(optionalPgClass.isPresent()&&optionalRace.isPresent()) return DefaultResponse.noMessage(characterService.filterByRaceAndClass(optionalRace.get(),optionalPgClass.get()),HttpStatus.OK);
        if(optionalPgClass.isPresent()) return DefaultResponse.noMessage(characterService.filterByClass(optionalPgClass.get()),HttpStatus.OK);
        if(optionalRace.isPresent()) return DefaultResponse.noMessage(characterService.filterByRace(optionalRace.get()),HttpStatus.OK);
        return DefaultResponse.noMessage(characterService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/characters/{userId}/getChar")
    public ResponseEntity<DefaultResponse> getUserChar(@PathVariable long userId) throws NotFoundException {
        return DefaultResponse.noMessage(characterService.findCharByUserId(userId),HttpStatus.OK);
    }
    @PostMapping("/characters")
    public ResponseEntity<DefaultResponse> save(@RequestBody @Validated CharacterRequest characterRequest, BindingResult bindingResult) throws BadRequestExceptionHandler, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestExceptionHandler(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.noMessage(characterService.save(characterRequest),HttpStatus.CREATED);
    }
    @PutMapping("/characters/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable long id,@RequestBody @Validated CharacterRequest characterRequest, BindingResult bindingResult) throws BadRequestExceptionHandler, NotFoundException {
        if(bindingResult.hasErrors())
            throw new BadRequestExceptionHandler(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.noMessage(characterService.update(id,characterRequest),HttpStatus.CREATED);
    }
    @PatchMapping("/characters/{id}/upload")
    public ResponseEntity<DefaultResponse> upload(@PathVariable long id, @RequestParam("upload") MultipartFile file) throws IOException, NotFoundException {
        Character character = characterService.upload(id, (String)cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
        return DefaultResponse.full("Image uploaded", character , HttpStatus.OK);
    }
    @DeleteMapping("/characters/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable long id) throws NotFoundException {
        characterService.delete(id);
        return DefaultResponse.noObject("Deleted",HttpStatus.OK);
    }
}
