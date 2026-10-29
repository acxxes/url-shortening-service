package com.github.acxxes.urlshortener.repository;

import com.github.acxxes.urlshortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// talks to database
// is the database repository
public interface UrlRepository extends JpaRepository<Url, Long> { // <EntityType, TypeOfIdField>

    Optional<Url> findByShortCode(String shortCode); // SELECT ... WHERE short_code = ?
    boolean existsByShortCode(String shortCode);    // for collision check
    void deleteByShortCode(String shortCode);

    @Modifying
    @Transactional
    @Query("UPDATE Url SET accessCount = accessCount + 1 WHERE shortCode = :sc")
    void incrementAccessCount(@Param("sc") String sc);

}
