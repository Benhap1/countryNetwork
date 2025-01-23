package ru.skillbox.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.country.entity.City;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {

    List<City> findAllByCountryId(UUID countryUUID);

    Optional<City> findByTitle(String title);
}
