package ru.skillbox.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.country.entity.City;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {

//    List<City> findAllByCountryId(UUID countryUUID);

    @Query("SELECT c FROM City c JOIN FETCH c.country WHERE c.country.id = :countryUUID")
    List<City> findAllByCountryId(@Param("countryUUID") UUID countryUUID);

    @Query("SELECT c.title FROM City c WHERE c.country.id = :countryUUID")
    Set<String> findAllTitlesByCountryId(@Param("countryUUID") UUID countryUUID);


    Optional<City> findByTitle(String title);
}
