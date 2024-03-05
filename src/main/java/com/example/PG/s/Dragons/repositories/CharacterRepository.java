package com.example.PG.s.Dragons.repositories;

import com.example.PG.s.Dragons.entities.Character;
import com.example.PG.s.Dragons.enums.PgClass;
import com.example.PG.s.Dragons.enums.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface CharacterRepository extends JpaRepository<Character,Long>, PagingAndSortingRepository<Character,Long> {
    Page<Character> findByPgClassOrderByNameAsc (Pageable pageable,PgClass pgClass);
    Page<Character> findByRaceOrderByNameAsc (Pageable pageable, Race race);
    Page<Character> findByRaceAndPgClassOrderByNameAsc(Pageable pageable,Race race,PgClass pgClass);
}
