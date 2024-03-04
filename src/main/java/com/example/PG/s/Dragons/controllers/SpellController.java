package com.example.PG.s.Dragons.controllers;

import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.responses.DefaultResponse;
import com.example.PG.s.Dragons.services.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spells")
public class SpellController {
    @Autowired
    private SpellService spellService;
    @GetMapping
    public ResponseEntity<DefaultResponse> getAll(){
        return DefaultResponse.noMessage(spellService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/page")
    public ResponseEntity<DefaultResponse> getPage(Pageable pageable){
        return DefaultResponse.noMessage(spellService.findPage(pageable),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> getById(@PathVariable long id) throws NotFoundException {
        return DefaultResponse.noMessage(spellService.findById(id),HttpStatus.OK);
    }
}
