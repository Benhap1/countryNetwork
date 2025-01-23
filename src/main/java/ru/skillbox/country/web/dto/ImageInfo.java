package ru.skillbox.country.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageInfo {

    @Schema(implementation = String.class, example = "https://skb44gr.storage.yandexcloud.net/...")
    private String fileName;

}
