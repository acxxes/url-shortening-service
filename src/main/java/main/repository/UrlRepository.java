package main.repository;

import main.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// talks to database
// database repository
@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    // <EntityType, TypeOfIdField>

    List<Url> findByUrl(String url);

    Url findById(long id);

}
