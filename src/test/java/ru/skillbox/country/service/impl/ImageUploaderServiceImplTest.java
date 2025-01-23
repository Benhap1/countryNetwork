package ru.skillbox.country.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.country.web.dto.ImageInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ImageUploaderServiceImplTest {

    @Mock
    private AmazonS3 amazonS3;

    @Value("${app.storage.test.bucketName}")
    private String testBucketName;

    @Value("${app.storage.test.fileId}")
    private String testFileId;

    @Value("${app.storage.test.urlFileFromStorage}")
    private String urlFileFromStorage;

    @InjectMocks
    private ImageUploaderServiceImpl imageUploaderServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SneakyThrows
    @Disabled
    void thenUploadPhotoToStorageThenReturnURLPhoto() {
        MockMultipartFile fileForTest = new MockMultipartFile("file1", "file1.jpg", "image/jpeg", "image data 1".getBytes());
        ObjectMetadata metadata = new ObjectMetadata();

        when(amazonS3.putObject(testBucketName, testFileId, new ByteArrayInputStream(fileForTest.getBytes()), metadata))
                .thenReturn(new PutObjectResult());
        when(amazonS3.getUrl(testBucketName, testFileId)).thenReturn(new URL(urlFileFromStorage));

        ImageInfo actualImageInfo = imageUploaderServiceImpl.uploadPhoto(fileForTest);
        ImageInfo expectedImageInfo = ImageInfo.builder().fileName(urlFileFromStorage).build();

        assertEquals(expectedImageInfo, actualImageInfo);
    }
}