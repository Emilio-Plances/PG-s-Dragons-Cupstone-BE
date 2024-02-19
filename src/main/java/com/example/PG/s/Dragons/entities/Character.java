package com.example.PG.s.Dragons.entities;

import jakarta.persistence.*;
import lombok.Data;

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
}
