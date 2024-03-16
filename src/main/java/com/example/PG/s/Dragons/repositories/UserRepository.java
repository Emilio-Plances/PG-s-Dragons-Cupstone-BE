package com.example.PG.s.Dragons.repositories;


import com.example.PG.s.Dragons.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, PagingAndSortingRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.publicUsername) LIKE LOWER(CONCAT('%', :publicUsername, '%')) ORDER BY u.username ASC")
    Page<User> searchByPublicUsername(Pageable pageable, String publicUsername);
}
