package com.example.PG.s.Dragons.services;

import com.example.PG.s.Dragons.entities.Character;
import com.example.PG.s.Dragons.entities.Spell;
import com.example.PG.s.Dragons.entities.User;
import com.example.PG.s.Dragons.enums.PgClass;
import com.example.PG.s.Dragons.enums.Race;
import com.example.PG.s.Dragons.enums.Status;
import com.example.PG.s.Dragons.exceptions.NotFoundException;
import com.example.PG.s.Dragons.repositories.CharacterRepository;
import com.example.PG.s.Dragons.requests.characterRequests.CharacterPutRequest;
import com.example.PG.s.Dragons.requests.characterRequests.CharacterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CharacterService {
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SpellService spellService;
    public List<Character> findAll(){
        return characterRepository.findAll();
    }
    public List<Character> filterByClass(PgClass pgClass){
        return characterRepository.findByPgClassOrderByNameAsc(pgClass);
    }
    public List<Character> filterByRace(Race race){
        return characterRepository.findByRaceOrderByNameAsc(race);
    }
    public List<Character> filterByRaceAndClass(Race race,PgClass pgClass){
        return characterRepository.findByRaceAndPgClassOrderByNameAsc(race,pgClass);
    }
    public Character findById(long id) throws NotFoundException {
        return characterRepository.findById(id).orElseThrow(()->new NotFoundException("Character not found"));
    }
    public List<Character> findCharByUserId(long id) throws NotFoundException {
        return characterRepository.findByUserId(id);
    }
    public Character save(CharacterRequest characterRequest) throws NotFoundException {
        User user= userService.findById(characterRequest.getUserId());
        Character character=new Character();
        character.setUser(user);
        character.setStatus(Status.Private);
        return characterRepository.save(character);
    }
    public Character update(long id, CharacterPutRequest characterPutRequest) throws NotFoundException {
        Character character=findById(id);
        character.setStatus(characterPutRequest.getStatus());
        return characterRepository.save(copy(characterPutRequest,character));
    }
    public Character upload(long id, String link) throws NotFoundException {
        Character character=findById(id);
        character.setImage(link);

        return characterRepository.save(character);
    }
    public void delete(long id) throws NotFoundException {
        Character character=findById(id);
        characterRepository.delete(character);
    }
    private Set<Spell> setSpells(Set<Long> spellsIds){
        Set<Spell> spells=new HashSet<>();
        if(spellsIds==null) return null;
        spellsIds.forEach(el-> {
            try {spells.add(spellService.findById(el));}
            catch (NotFoundException e) {throw new RuntimeException(e);}
        });
        return spells;
    }
    private Character copy(CharacterPutRequest characterRequest,Character character){
        character.setName(characterRequest.getName());
        character.setPgClass(characterRequest.getPgClass());
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
        character.setImage(characterRequest.getImage());
        return character;
    }
}
