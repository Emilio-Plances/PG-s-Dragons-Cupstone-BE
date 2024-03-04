package com.example.PG.s.Dragons.services;

import com.example.PG.s.Dragons.entities.Character;
import com.example.PG.s.Dragons.entities.Spell;
import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.repositories.CharacterRepository;
import com.example.PG.s.Dragons.requests.characterRequests.CharacterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CharacterService {
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SpellService spellService;

    public Page<Character> findAll(Pageable pageable){
        return characterRepository.findAll(pageable);
    }
    public Character findById(long id) throws NotFoundException {
        return characterRepository.findById(id).orElseThrow(()->new NotFoundException("Character not found"));
    }
    public Character save(CharacterRequest characterRequest) throws NotFoundException {
        User user= userService.findById(characterRequest.getUserId());
        Character character=new Character();
        character.setUser(user);
        return characterRepository.save(copy(characterRequest,character));
    }
    public Character update(long id, CharacterRequest characterRequest) throws NotFoundException {
        Character character=findById(id);
        return characterRepository.save(copy(characterRequest,character));
    }
    private Character copy(CharacterRequest characterRequest,Character character){
        character.setStatus(characterRequest.getStatus());
        character.setName(characterRequest.getName());
        character.setLevel(characterRequest.getLevel());
        character.setClassArmor(characterRequest.getClassArmor());
        character.setDice(characterRequest.getDice());
        character.setProficiency(characterRequest.getProficiency());
        character.setInitiative(characterRequest.getInitiative());
        character.setRace(characterRequest.getRace());
        character.setHp(characterRequest.getHp());
        character.setBackground(characterRequest.getBackground());
        character.setAlignment(characterRequest.getAlignment());
        character.setBackgroundText(characterRequest.getBackgroundText());
        character.setStrenght(characterRequest.getStrenght());
        character.setDexterity(characterRequest.getDexterity());
        character.setConstitution(characterRequest.getConstitution());
        character.setIntelligence(characterRequest.getIntelligence());
        character.setWisdom(characterRequest.getWisdom());
        character.setCharisma(characterRequest.getCharisma());
        character.setSpells(setSpells(characterRequest.getSpellsId()));
        character.setSkills(characterRequest.getSkills());
        return character;
    }
    private Set<Spell> setSpells(Set<Long> spellsIds){
        Set<Spell> spells=new HashSet<>();
        spellsIds.forEach(el-> {
            try {spells.add(spellService.findById(el));}
            catch (NotFoundException e) {throw new RuntimeException(e);}
        });
        return spells;
    }
    public void delete(long id) throws NotFoundException {
        Character character=findById(id);
        characterRepository.delete(character);
    }
}
