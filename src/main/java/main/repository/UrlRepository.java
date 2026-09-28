package main.repository;

import main.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// talks to database
@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    // <EntityType, TypeOfIdField>
}
