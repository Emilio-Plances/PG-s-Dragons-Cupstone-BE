package com.example.PG.s.Dragons.repositories;

import com.example.PG.s.Dragons.entities.Character;
import com.example.PG.s.Dragons.entities.Spell;
import com.example.PG.s.Dragons.enums.PgClass;
import com.example.PG.s.Dragons.enums.Race;
import com.example.PG.s.Dragons.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character,Long>{
    List<Character> findByPgClassAndStatusOrderByNameAsc (PgClass pgClass,Status status);
    List<Character> findByRaceAndStatusOrderByNameAsc (Race race,Status status);
    List<Character> findByRaceAndPgClassAndStatusOrderByNameAsc(Race race,PgClass pgClass,Status status);
    List<Character> findByStatusOrderByNameAsc(Status status);
    @Query("SELECT c FROM Character c WHERE c.user.id=:id")
    List<Character> findByUserId(long id);
    @Query("SELECT c FROM Character c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))AND c.status=Public ORDER BY c.name ASC")
    List<Character> searchByName(String name);
}
