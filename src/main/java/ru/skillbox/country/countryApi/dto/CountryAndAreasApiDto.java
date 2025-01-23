package ru.skillbox.country.countryApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryAndAreasApiDto {

    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "parent_id")
    private Object parent_id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "areas")
    private List<AreaApiDto> areaApiDtos = new ArrayList<>();

}
