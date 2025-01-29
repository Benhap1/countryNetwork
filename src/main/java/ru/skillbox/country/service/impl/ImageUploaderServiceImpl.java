package ru.skillbox.country.service.impl;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.AmazonS3Exception;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import ru.skillbox.country.service.ImageUploaderService;
//import ru.skillbox.country.web.dto.ImageInfo;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ImageUploaderServiceImpl implements ImageUploaderService {
//
//    @Value("${app.storage.bucketName}")
//    private String bucketName;
//
//    private final AmazonS3 s3Client;
//
//    @Override
//    public ImageInfo uploadPhoto(MultipartFile file) {
//        byte[] imageContent = getBytesFrom(file);
//        String urlImage = getURLImage(imageContent);
//
//        return ImageInfo.builder()
//                .fileName(urlImage)
//                .build();
//    }
//
//    private byte[] getBytesFrom(MultipartFile file) {
//        byte[] imageContent;
//        try {
//            imageContent = file.getBytes();
//        } catch (IOException e) {
//            throw new RuntimeException("Ошибка с чтения файла из запроса");
//        }
//
//        return imageContent;
//    }
//
//    private String getURLImage(byte[] imageContent) {
//        String urlImage;
//        try {
//            String imageFileIdForStorage = generateUUIDForFile();
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(imageContent.length);
//
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageContent);
//            s3Client.putObject(bucketName, imageFileIdForStorage, inputStream, metadata);
//            log.info("Сервис загрузки. Добавлен файл: {}  в корзину: {}", imageFileIdForStorage, bucketName);
//
//            urlImage = s3Client.getUrl(bucketName, imageFileIdForStorage).toExternalForm();
//
//        } catch (AmazonS3Exception e) {
//            log.error("Ошибка загрузки файлов в облако. Причина: {}", e.getMessage());
//            throw new AmazonS3Exception(e.getMessage());
//        }
//        return urlImage;
//    }
//
//    private String generateUUIDForFile() {
//        return UUID.randomUUID().toString();
//    }
//}

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.country.service.ImageUploaderService;
import ru.skillbox.country.web.dto.ImageInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploaderServiceImpl implements ImageUploaderService {

    @Value("${app.storage.bucketName}")
    private String bucketName;

    @Value("${app.storage.accesKeyId}")
    private String accessKeyId;

    @Value("${app.storage.secretKey}")
    private String secretKey;

    private final MinioClient minioClient;

    @Override
    public ImageInfo uploadPhoto(MultipartFile file) {
        byte[] imageContent = getBytesFrom(file);
        String urlImage = getURLImage(imageContent);

        return ImageInfo.builder()
                .fileName(urlImage)
                .build();
    }

    private byte[] getBytesFrom(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка с чтения файла из запроса");
        }
    }

    private String getURLImage(byte[] imageContent) {
        String urlImage;
        try {
            String imageFileIdForStorage = generateUUIDForFile();

            // Загрузка файла в MinIO с использованием PutObjectArgs
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageContent);
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(imageFileIdForStorage)
                    .stream(inputStream, imageContent.length, -1)  // -1 для неограниченной длины потока
                    .contentType("application/octet-stream")
                    .build();

            minioClient.putObject(putObjectArgs);
            log.info("Сервис загрузки. Добавлен файл: {} в корзину: {}", imageFileIdForStorage, bucketName);

            // Прямой URL для доступа к объекту
            urlImage = "http://localhost:9000/" + bucketName + "/" + imageFileIdForStorage;

        } catch (MinioException | IOException e) {
            log.error("Ошибка загрузки файлов в MinIO. Причина: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return urlImage;
    }

    private String generateUUIDForFile() {
        return UUID.randomUUID().toString();
    }
}
