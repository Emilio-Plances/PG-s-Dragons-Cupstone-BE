package com.example.PG.s.Dragons.repositories;

import com.example.PG.s.Dragons.entities.Spell;
import com.example.PG.s.Dragons.enums.PgClass;
import com.example.PG.s.Dragons.enums.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SpellRepository extends JpaRepository<Spell,Long>, PagingAndSortingRepository<Spell,Long> {
    @Query("SELECT s FROM Spell s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY s.name ASC")
    Page<Spell> searchByName(String name, Pageable pageable);
    @Query("SELECT s FROM Spell s WHERE s.school=:school ORDER BY s.name ASC" )
    Page<Spell> findBySchool(School school,Pageable pageable);
    @Query("SELECT s FROM Spell s WHERE :pgClass IN (s.pgClassList) ORDER BY s.name ASC" )
    Page<Spell> findByClass(PgClass pgClass, Pageable pageable);
}
