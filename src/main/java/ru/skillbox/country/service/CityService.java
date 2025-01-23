package ru.skillbox.country.service;

import ru.skillbox.country.web.dto.CityDto;

import java.util.List;
import java.util.UUID;

public interface CityService {

    List<CityDto> getCitiesByCountryId(UUID countryUUID);

}
