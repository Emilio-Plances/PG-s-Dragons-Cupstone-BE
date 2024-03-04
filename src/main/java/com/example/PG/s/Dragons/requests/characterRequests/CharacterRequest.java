package com.example.PG.s.Dragons.requests.characterRequests;

import com.example.PG.s.Dragons.entities.Spell;
import com.example.PG.s.Dragons.enums.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashMap;
import java.util.Set;
@Data
public class CharacterRequest {
    @NotNull
    private Integer userId;
    private Status status;
    private String name;
    private HashMap<PgClass,Integer> level=new HashMap<>();
    private int classArmor;
    private Dice dice;
    private int proficiency;
    private int initiative;
    private Race race;
    private Integer hp;
    private Background background;
    private Alignment alignment;
    private String backgroundText;
    private Integer strenght;
    private Integer dexterity;
    private Integer constitution;
    private Integer intelligence;
    private Integer wisdom;
    private Integer charisma;
    private Set<Long> spellsId;
    private Set<Skill> skills;
    private String image;
}
