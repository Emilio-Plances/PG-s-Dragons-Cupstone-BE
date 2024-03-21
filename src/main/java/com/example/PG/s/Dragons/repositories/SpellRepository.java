package com.example.PG.s.Dragons.repositories;

import com.example.PG.s.Dragons.entities.Spell;
import com.example.PG.s.Dragons.enums.PgClass;
import com.example.PG.s.Dragons.enums.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SpellRepository extends JpaRepository<Spell,Long>{

    @Query("SELECT s FROM Spell s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY s.name ASC")
    List<Spell> searchByNameAsc(String name);
    @Query("SELECT s FROM Spell s WHERE s.school=:school ORDER BY s.name ASC" )
    List<Spell> findBySchoolAsc(School school);
    @Query(value = "SELECT * FROM spells WHERE :pgClass = ANY(classes)",nativeQuery = true)
    List<Spell> findByClassAsc(String pgClass);

}
