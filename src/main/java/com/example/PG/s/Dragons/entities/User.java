package com.example.PG.s.Dragons.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence",allocationSize = 1,initialValue = 1)
    private long id;
    @Column(unique = true)
    private String username;
    private String name;
    private String surname;
    private String password;
    private String info;
    private String linkPhoto;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Character> characterList;

    @Override
    public String toString() {
        return  "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", info='" + info + '\'' +
                ", linkPhoto='" + linkPhoto + '\'';
    }
}
