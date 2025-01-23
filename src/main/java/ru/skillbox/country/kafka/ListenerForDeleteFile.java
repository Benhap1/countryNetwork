package ru.skillbox.country.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.skillbox.common.events.DeleteFileEvent;
import ru.skillbox.country.exception.UnsuccessfulDeletionListImages;
import ru.skillbox.country.service.ImageDeleteService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListenerForDeleteFile {

    private final ImageDeleteService imageDeleteService;

    @KafkaListener(
            topics = "${app.kafka.fileDeleteTopic}",
            groupId = "${app.kafka.fileDeleteGroupId}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
    public void listenForDeleteFile(@Payload DeleteFileEvent event,
                                    @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("Received event: {}", event);
        log.info("Message: {}; Topic: {}, Time: {}", event, topic, System.currentTimeMillis());

        List<String> urlsImagesForDelete = Optional
                .ofNullable(event.getUrlsImageForDelete())
                .orElse(List.of());

        if (!urlsImagesForDelete.isEmpty()) {
            boolean isDeletedImages = imageDeleteService.deleteImage(urlsImagesForDelete);

            if (isDeletedImages) {
                log.info("Картинки по ссылкам: {} удалены", urlsImagesForDelete);
            } else {
                log.warn("Картинки по ссылкам: {} не удалены, что то пошло не так", urlsImagesForDelete);
                throw new UnsuccessfulDeletionListImages("Не удачное удаление изображений");
            }
        } else {
            log.warn("Список ссылок для удаления пуст");
        }
    }
}
