package ru.skillbox.country.web.controller.country;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.country.web.dto.CityDto;
import ru.skillbox.country.web.dto.CountryDto;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/geo")
public interface CountryApi {


    @Operation(
            summary = "Получение всех страна",
            description = "Получить все страны из базы"
    )
    @GetMapping("/country")
    List<CountryDto> countries();

    @Operation(
            summary = "Получение городов по стране",
            description = "Получение городов по id необходимой страны",
            parameters = {
                    @Parameter(
                            name = "countryId",
                            description = "Id страны по которой нужно получить города",
                            schema = @Schema(implementation = UUID.class, example = "a78a3148-0dd9-4a18-bbd3-ed43014a0e37"),
                            in = ParameterIn.PATH,
                            required = true
                    )
            }
    )
    @GetMapping("/country/{countryId}/city")
    List<CityDto> countryId(@PathVariable(value = "countryId") UUID countryId);
}
