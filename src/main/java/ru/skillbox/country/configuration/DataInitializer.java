package ru.skillbox.country.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.skillbox.country.service.CityService;
import ru.skillbox.country.service.CountryService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final CountryService countryService;
    private final CityService cityService;

    @PostConstruct
    public void initializeData() {
        log.info("Инициализация данных...");
        countryService.saveCountriesToBD();
        cityService.saveCitiesToBD();
        log.info("Инициализация данных завершена.");
    }
}
