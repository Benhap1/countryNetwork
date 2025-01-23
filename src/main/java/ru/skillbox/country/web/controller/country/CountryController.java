package ru.skillbox.country.web.controller.country;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.country.service.CityService;
import ru.skillbox.country.service.CountryService;
import ru.skillbox.country.web.dto.CityDto;
import ru.skillbox.country.web.dto.CountryDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CountryController implements CountryApi {

    private final CountryService countryService;

    private final CityService cityService;

    @Override
    public List<CountryDto> countries() {
        return countryService.getCountries();
    }

    @Override
    public List<CityDto> countryId(UUID countryId) {
        return cityService.getCitiesByCountryId(countryId);
    }
}
