package ru.skillbox.country.web.controller.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.country.service.ImageUploaderService;
import ru.skillbox.country.web.dto.ImageInfo;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageUploaderApiController implements ImageUploaderApi {

    private final ImageUploaderService imageUploaderService;

    @Override
    public ImageInfo uploadData(MultipartFile file) {
        log.info("======================= Uploading image ==============================");
        return imageUploaderService.uploadPhoto(file);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public void handlePreflight() {
        log.info("OPTIONS REQUEST");
        // Обработка пустого ответа на OPTIONS-запрос
    }
}
