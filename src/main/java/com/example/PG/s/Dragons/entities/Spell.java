package com.example.PG.s.Dragons.entities;

import com.example.PG.s.Dragons.enums.Class;
import com.example.PG.s.Dragons.enums.School;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "spells")
public class Spell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "classes")
    private Set<Class> classList;
    private int level;
    @Enumerated(EnumType.STRING)
    private School school;
    @Column(name = "cast_time")
    private String castTime;
    private String range;
    private String duration;
    private List<Boolean> components;
    @Column(name = "material_cost",columnDefinition = "TEXT")
    private String materialCost;
    @Column(columnDefinition = "TEXT")
    private String description;
}
