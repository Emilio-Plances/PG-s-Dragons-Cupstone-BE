package com.example.PG.s.Dragons.repositories;

import com.example.PG.s.Dragons.entities.Character;
import com.example.PG.s.Dragons.enums.PgClass;
import com.example.PG.s.Dragons.enums.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character,Long>{
    List<Character> findByPgClassOrderByNameAsc (PgClass pgClass);
    List<Character> findByRaceOrderByNameAsc (Race race);
    List<Character> findByRaceAndPgClassOrderByNameAsc(Race race,PgClass pgClass);
    @Query("SELECT c FROM Character c WHERE c.user.id=:id")
    List<Character> findByUserId(long id);
}
