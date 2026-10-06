package com.github.acxxes.urlshortener.repository;

import com.github.acxxes.urlshortener.entity.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// talks to database
// is the database repository
@Repository
public interface URLRepository extends JpaRepository<URL, Long> { // <EntityType, TypeOfIdField>

    Optional<URL> findByShortCode(String shortCode); // SELECT ... WHERE short_code = ?
    boolean existsByShortCode(String shortCode);    // for collision check
    void deleteByShortCode(String shortCode);

}
