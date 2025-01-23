package ru.skillbox.country.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ExceptionMessage handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return new ExceptionMessage("Загружаемый файл превышает допустимый размер 20 Мб");
    }

    @ExceptionHandler(UnsuccessfulDeletionListImages.class)
    public ExceptionMessage handleUnsuccessfulDeletionListImages(UnsuccessfulDeletionListImages e) {
        return new ExceptionMessage(e.getMessage());
    }

}
