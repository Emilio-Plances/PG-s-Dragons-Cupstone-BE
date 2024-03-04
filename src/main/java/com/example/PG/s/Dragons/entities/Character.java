package com.example.PG.s.Dragons.entities;

import com.example.PG.s.Dragons.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Set;

@Data
@Entity
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "characters_sequence")
    @SequenceGenerator(name = "characters_sequence",allocationSize = 1,initialValue = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_fk")
    private User user;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String name;
    private HashMap<PgClass,Integer> level=new HashMap<>();
    @Column(name = "class_armor")
    private int classArmor;
    @Enumerated(EnumType.STRING)
    private Dice dice;
    private int proficiency;
    private int initiative;
    @Enumerated(EnumType.STRING)
    private Race race;
    private Integer hp;
    @Enumerated(EnumType.STRING)
    private Background background;
    @Enumerated(EnumType.STRING)
    private Alignment alignment;
    @Column(columnDefinition = "TEXT",name = "background_text")
    private String backgroundText;
    private Integer strenght;
    private Integer dexterity;
    private Integer constitution;
    private Integer intelligence;
    private Integer wisdom;
    private Integer charisma;
    @OneToMany
    @JoinColumn(name = "spells_fk")
    private Set<Spell> spells;
    @Enumerated(EnumType.STRING)
    private Set<Skill> skills;
}
