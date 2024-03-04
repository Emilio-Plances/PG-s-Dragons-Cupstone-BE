package com.example.PG.s.Dragons.repositories;

import com.example.PG.s.Dragons.entities.Spell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SpellRepository extends JpaRepository<Spell,Long>, PagingAndSortingRepository<Spell,Long> {
}
