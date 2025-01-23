package ru.skillbox.country.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.country.entity.Country;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {

    @EntityGraph(attributePaths = "cities")
    Optional<Country> findByTitle(String title);

    List<Country> findAll();
}
