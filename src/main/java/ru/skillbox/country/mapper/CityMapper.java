package ru.skillbox.country.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skillbox.country.entity.City;
import ru.skillbox.country.web.dto.CityDto;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CityMapper {

    @Mapping(target = "countryId", expression = "java(city.getCountry().getId())")
    public abstract CityDto fromCityEntityToCityDto(City city);

    public abstract List<CityDto> fromListCityToListCityDto(List<City> allByCountryId);


}
