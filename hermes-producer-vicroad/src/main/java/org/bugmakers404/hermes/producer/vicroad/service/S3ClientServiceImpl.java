package org.bugmakers404.hermes.producer.vicroad.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsArchiveService;
import org.bugmakers404.hermes.producer.vicroad.util.Constants;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
@Profile("prod")
@RequiredArgsConstructor
public class S3ClientServiceImpl implements EventsArchiveService {

  private final S3Client s3Client;

  public void archiveEvents(@NonNull String topic, @NonNull OffsetDateTime timestamp,
      String events) {

    String filePath = Constants.BLUETOOTH_DATA_ARCHIVES_EVENT_PATH.formatted(topic,
        timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME));

    try {
      saveStringAsJsonFile(filePath, events);
      log.info("{} - Succeed to archive events in S3 bucket {}", topic,
          Constants.HERMES_DATA_BUCKET_NAME);
    } catch (Exception e) {
      log.error("{} - Failed to archive events in S3 bucket {}: {}", topic,
          Constants.HERMES_DATA_BUCKET_NAME, e.getMessage(), e);
      storeEventsToLocalFiles(topic, filePath, events);
    }
  }

  private void saveStringAsJsonFile(String objectKey, String jsonString) {

    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(Constants.HERMES_DATA_BUCKET_NAME).key(objectKey).contentType("application/json")
        .build();

    RequestBody requestBody = RequestBody.fromString(jsonString, StandardCharsets.UTF_8);
    s3Client.putObject(putObjectRequest, requestBody);
  }

  private void storeEventsToLocalFiles(String topic, String filePath, String content) {

    Path targetPath = Paths.get(filePath);

    try {
      Files.createDirectories(targetPath.getParent());
      Files.writeString(targetPath, content);
      log.info("{} - Succeed to archive the failed events locally at {}", topic, filePath);
    } catch (IOException e) {
      log.error("{} - Failed to archive the failed events locally: {}", topic, e.getMessage(), e);
    }
  }
}
