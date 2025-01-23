package ru.skillbox.country.web.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto implements Serializable {

    @Schema(implementation = UUID.class, example = "a78a3148-0dd9-4a18-bbd3-ed43014a0e37")
    private UUID id;

    @Schema(implementation = String.class, example = "Россия")
    private String title;

    @ArraySchema(schema = @Schema(implementation = CityDto.class))
    private List<CityDto> cities = new ArrayList<>();
}
