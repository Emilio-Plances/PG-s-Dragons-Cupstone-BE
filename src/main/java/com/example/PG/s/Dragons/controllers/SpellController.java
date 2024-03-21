package com.example.PG.s.Dragons.controllers;

import com.example.PG.s.Dragons.enums.PgClass;
import com.example.PG.s.Dragons.enums.School;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.responses.DefaultResponse;
import com.example.PG.s.Dragons.services.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/noAuth/spells")
public class SpellController {
    @Autowired
    private SpellService spellService;
    @GetMapping
    public ResponseEntity<DefaultResponse> getAll(){
        return DefaultResponse.noMessage(spellService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> getById(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.noMessage(spellService.findById(id),HttpStatus.OK);
    }
    @GetMapping("/name")
    public ResponseEntity<DefaultResponse> getByName(@RequestParam String name){
        return DefaultResponse.noMessage(spellService.searchByNameAsc(name),HttpStatus.OK);
    }
    @GetMapping("/class")
    public ResponseEntity<DefaultResponse> getByClass(@RequestParam PgClass pgClass){
        return DefaultResponse.noMessage(spellService.findByClassAsc(pgClass),HttpStatus.OK);
    }
    @GetMapping("/school")
    public ResponseEntity<DefaultResponse> getBySchool(@RequestParam School school){
        return DefaultResponse.noMessage(spellService.findBySchoolAsc(school),HttpStatus.OK);
    }

}
