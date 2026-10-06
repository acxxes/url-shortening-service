package com.github.acxxes.urlshortener.repository;

import com.github.acxxes.urlshortener.entity.Url;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// talks to database
// is the database repository
@Repository
public interface UrlRepository extends JpaRepository<Url, Long> { // <EntityType, TypeOfIdField>

    Optional<Url> findByShortCode(String shortCode); // SELECT ... WHERE short_code = ?
    boolean existsByShortCode(String shortCode);    // for collision check
    void deleteByShortCode(String shortCode);

}
