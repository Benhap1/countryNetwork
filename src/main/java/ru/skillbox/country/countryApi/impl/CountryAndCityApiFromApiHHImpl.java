package ru.skillbox.country.countryApi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.skillbox.country.countryApi.CountryAndCityApiFromApiHH;
import ru.skillbox.country.countryApi.dto.CountryAndAreasApiDto;

@Component
@RequiredArgsConstructor
public class CountryAndCityApiFromApiHHImpl implements CountryAndCityApiFromApiHH {

    private final WebClient webClient;

    @Override
    public CountryAndAreasApiDto[] getCountryAndCitiesJSON() {
        return webClient
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CountryAndAreasApiDto[].class)
                .block();
    }
}
