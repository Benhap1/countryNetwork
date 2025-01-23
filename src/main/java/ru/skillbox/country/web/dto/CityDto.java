package ru.skillbox.country.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    @Schema(implementation = UUID.class, example = "a78a3148-0dd9-4a18-bbd3-ed43014a0e37")
    private UUID id;

    @Schema(implementation = String.class, example = "Москва")
    private String title;

    @Schema(implementation = UUID.class, example = "a78a3148-0dd9-4a18-bbd3-ed43014a0e37")
    private UUID countryId;

}
