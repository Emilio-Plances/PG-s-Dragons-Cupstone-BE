package com.example.PG.s.Dragons.repositories;

import com.example.PG.s.Dragons.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CharacterRepository extends JpaRepository<Character,Long>, PagingAndSortingRepository<Character,Long> {
}
