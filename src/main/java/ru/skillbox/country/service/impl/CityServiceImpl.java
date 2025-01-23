package ru.skillbox.country.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.country.countryApi.CountryAndCityApiFromApiHH;
import ru.skillbox.country.countryApi.dto.AreaApiDto;
import ru.skillbox.country.countryApi.dto.CountryAndAreasApiDto;
import ru.skillbox.country.entity.City;
import ru.skillbox.country.entity.Country;
import ru.skillbox.country.mapper.CityMapper;
import ru.skillbox.country.repository.CityRepository;
import ru.skillbox.country.repository.CountryRepository;
import ru.skillbox.country.service.CityService;
import ru.skillbox.country.web.dto.CityDto;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final CountryRepository countryRepository;

    private final CityMapper cityMapper;

    private final CountryAndCityApiFromApiHH countryAndCityApiFromApiHH;

    @Override
    public List<CityDto> getCitiesByCountryId(UUID countryUUID) {
        return cityMapper.fromListCityToListCityDto(cityRepository.findAllByCountryId(countryUUID));
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 1/12 *")
    public void saveCitiesToBD() {
        CountryAndAreasApiDto[] jsonFromApi = countryAndCityApiFromApiHH.getCountryAndCitiesJSON();
        List<CountryAndAreasApiDto> countryAndAreasApiDtoList = Arrays.asList(jsonFromApi);

        for (CountryAndAreasApiDto countryAndAreasApiDto : countryAndAreasApiDtoList) {
            if (countryAndAreasApiDto.getName().contains("Другие регионы")) {
                List<AreaApiDto> countries = countryAndAreasApiDto.getAreaApiDtos();
                countries.forEach(country -> {
                    parseCountryAndAreasApiDtoAndSaveCitiesToBd(country.getName(), country.getAreaApiDtos());
                });
            } else {
                parseCountryAndAreasApiDtoAndSaveCitiesToBd(countryAndAreasApiDto.getName(), countryAndAreasApiDto.getAreaApiDtos());
            }
        }
    }

    private void parseCountryAndAreasApiDtoAndSaveCitiesToBd(String countryName, List<AreaApiDto> areasOrCitiesCountry) {
        Country countryFromBd = countryRepository.findByTitle(countryName)
                .orElseThrow(() -> new RuntimeException
                        (MessageFormat.format("Country with name{0} not found", countryName)));
        areasOrCitiesCountry.forEach(areaDto -> {
            List<AreaApiDto> citiesArea = areaDto.getAreaApiDtos();
            if (citiesArea.isEmpty()) {
                createCityFrom(countryFromBd, areaDto.getName());
            } else {
                createCityFrom(countryFromBd, citiesArea);
            }
        });
        log.warn("Обновление базы городов прошло успешно");
    }

    private void createCityFrom(Country country, String nameCity) {
        Optional<City> byTitle = cityRepository.findByTitle(nameCity);
        if (byTitle.isEmpty()) {
            City city = new City();
            city.setCountry(country);
            city.setTitle(nameCity);

            cityRepository.save(city);
        }
    }

    private void createCityFrom(Country country, List<AreaApiDto> citiesArea) {
        List<City> listCityForSaveToBd = new ArrayList<>();
        citiesArea.forEach(cityDto -> {
            String nameCity = cityDto.getName();
            Optional<City> byTitle = cityRepository.findByTitle(nameCity);
            if (byTitle.isEmpty()) {
                City city = new City();
                city.setCountry(country);
                city.setTitle(nameCity);
                listCityForSaveToBd.add(city);
            }
        });
        cityRepository.saveAll(listCityForSaveToBd);
    }


}
