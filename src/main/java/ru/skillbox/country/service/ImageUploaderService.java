package ru.skillbox.country.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.country.web.dto.ImageInfo;

public interface ImageUploaderService {

    ImageInfo uploadPhoto(MultipartFile fil);

}
