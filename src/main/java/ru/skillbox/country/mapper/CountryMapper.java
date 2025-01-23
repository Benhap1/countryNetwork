package ru.skillbox.country.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.skillbox.country.countryApi.dto.AreaApiDto;
import ru.skillbox.country.countryApi.dto.CountryAndAreasApiDto;
import ru.skillbox.country.entity.Country;
import ru.skillbox.country.web.dto.CountryDto;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CityMapper.class})
public abstract class CountryMapper {

    public abstract CountryDto fromCountryToCountryDto(Country country);

    public abstract List<CountryDto> fromListCountryToListCountryDto(List<Country> all);

    public Country fromCountryAndAreasApiDto(CountryAndAreasApiDto countryAndAreasApiDto) {
        Country country = new Country();
        country.setTitle(countryAndAreasApiDto.getName());
        country.setCities(Collections.emptyList());

        return country;
    }

    public Country fromCountryAreasApiDto(AreaApiDto areaApiDto) {
        Country country = new Country();
        country.setTitle(areaApiDto.getName());
        country.setCities(Collections.emptyList());

        return country;
    }

    public abstract List<Country> fromCountryAndAreasApiDtoList(List<CountryAndAreasApiDto> countryAndAreasApiDtoList);
}
