package com.example.PG.s.Dragons.services;

import com.example.PG.s.Dragons.entities.Spell;
import com.example.PG.s.Dragons.enums.PgClass;
import com.example.PG.s.Dragons.enums.School;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.repositories.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpellService {
    @Autowired
    private SpellRepository spellRepository;
    public List<Spell> findAll(){
        return spellRepository.findAll();
    }
    public Spell findById(long id) throws NotFoundException {
        return spellRepository.findById(id).orElseThrow(()->new NotFoundException("Spell not found"));
    }
    public void save(Spell spell){
        spellRepository.save(spell);
    }
    public Optional<Spell> optionalById(long id) throws NotFoundException {
        return spellRepository.findById(id);
    }
    public List<Spell> searchByNameAsc(String name) {
        return spellRepository.searchByNameAsc(name);
    }

    public List<Spell> findByClassAsc(PgClass pgClass){
        return spellRepository.findByClassAsc(pgClass);
    }

    public List<Spell> findBySchoolAsc(School school){
        return spellRepository.findBySchoolAsc(school);
    }
}
