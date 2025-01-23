package ru.skillbox.country.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skillbox.country.service.ImageDeleteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageDeleteServiceImpl implements ImageDeleteService {

    @Value("${app.storage.bucketName}")
    private String bucketName;

    private final AmazonS3 s3Client;

    private final Map<String, Boolean> mapReportDeletedImages = new HashMap<>();

    @Override
    public Boolean deleteImage(List<String> urlsImagesForDelete) {
        boolean success = true;
        for (String url : urlsImagesForDelete) {
            try {
                DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, url);
                s3Client.deleteObject(deleteObjectRequest);

                addRecordToReportByDeleteImagesFromStorage(url);
            } catch (AmazonServiceException ex) {
                log.error("При удалении изображения по ссылке: {} произошло критическое исключение: {}", url, ex.getMessage());
                return !success;
            }
        }
        showLogReportByDeleteImage();

        return success;
    }

    private void addRecordToReportByDeleteImagesFromStorage(String url) {
        boolean hasImageInStorage = s3Client.doesObjectExist(bucketName, url);
        mapReportDeletedImages.put(url, hasImageInStorage);
    }

    private void showLogReportByDeleteImage() {
        for (Map.Entry<String, Boolean> entry : mapReportDeletedImages.entrySet()) {
            String url = entry.getKey();
            boolean isDeletedImage = entry.getValue();

            log.info("Изображение по ссылке: {} удалено -> {}", url, isDeletedImage);
        }
    }
}
