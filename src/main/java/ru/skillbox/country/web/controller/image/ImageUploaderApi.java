package ru.skillbox.country.web.controller.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.country.web.dto.ImageInfo;

@RequestMapping("api/v1/storage")
public interface ImageUploaderApi {

    @Operation(
            summary = "Загрузка картинок",
            description = "Загрузка картинки в Yandex Storage и  получение на нее ссылки",
            parameters = {
                    @Parameter(
                            description = "Файл который требуется отправить в хранилище",
                            required = true,
                            in = ParameterIn.QUERY,
                            schema = @Schema(
                                    implementation = MultipartFile.class
                            )
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ImageInfo uploadData(@RequestParam("file") MultipartFile file);
}
