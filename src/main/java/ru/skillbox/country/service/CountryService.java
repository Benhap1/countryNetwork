package ru.skillbox.country.service;

import ru.skillbox.country.web.dto.CountryDto;

import java.util.List;

public interface CountryService {

    List<CountryDto> getCountries();
    void saveCountriesToBD();

}
