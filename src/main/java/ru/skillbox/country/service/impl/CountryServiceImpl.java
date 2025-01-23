package ru.skillbox.country.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.country.countryApi.CountryAndCityApiFromApiHH;
import ru.skillbox.country.countryApi.dto.CountryAndAreasApiDto;
import ru.skillbox.country.entity.Country;
import ru.skillbox.country.mapper.CountryMapper;
import ru.skillbox.country.repository.CountryRepository;
import ru.skillbox.country.service.CountryService;
import ru.skillbox.country.web.dto.CountryDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final CountryMapper countryMapper;

    private final CountryAndCityApiFromApiHH countryAndCityApiFromApiHHImpl;

    @Override
    @Transactional(readOnly = true)
    public List<CountryDto> getCountries() {
        return countryMapper.fromListCountryToListCountryDto(countryRepository.findAll());
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 1/11 *")
    public void saveCountriesToBD() {
        System.out.println();
        CountryAndAreasApiDto[] jsonFromApi = countryAndCityApiFromApiHHImpl.getCountryAndCitiesJSON();
        List<CountryAndAreasApiDto> countryAndAreasApiDtoList = Arrays.asList(jsonFromApi);
        List<Country> countriesForSave = new ArrayList<>();
        for (CountryAndAreasApiDto dto : countryAndAreasApiDtoList) {
            if (dto.getName().contains("Другие регионы")) {
                List<Country> countryListAnotherRegions = dto.getAreaApiDtos().stream()
                        .map(countryMapper::fromCountryAreasApiDto).toList();
                countriesForSave.addAll(countryListAnotherRegions);
            } else {
                countriesForSave.add(countryMapper.fromCountryAndAreasApiDto(dto));
            }
        }
        countriesForSave.forEach(country -> {
            Country countryFromBD = countryRepository.findByTitle(country.getTitle()).orElse(null);
            if (countryFromBD == null) {
                countryRepository.save(country);
            }
        });
        log.warn("Обновление базы стран прошло успешно");
    }
}
