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
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
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

  @Override
  public void archiveLinkEvents(@NonNull OffsetDateTime timestamp, String linkEvents) {

    String filePath = Constants.LINKS_FILE_PATH.formatted(
        timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME));

    try {

      saveStringAsJsonFile(Constants.HERMES_DATA_BUCKET_NAME, filePath, linkEvents);
      log.info("{} - Succeed to archive data in S3 bucket {}",
          Constants.BLUETOOTH_DATA_TOPIC_LINKS, Constants.HERMES_DATA_BUCKET_NAME);

    } catch (Exception e) {

      storeEventsToLocalFiles(Constants.BLUETOOTH_DATA_TOPIC_LINKS, filePath, linkEvents);
      log.error("{} - Failed to archive data in S3 bucket {}: {}",
          Constants.BLUETOOTH_DATA_TOPIC_LINKS, Constants.HERMES_DATA_BUCKET_NAME, e.getMessage(),
          e);

    }
  }

  @Override
  public void archiveLinkWithGeoEvents(@NonNull OffsetDateTime timestamp,
      String linkWithGeoEvents) {

    String filePath = Constants.LINKS_WITH_GEO_FILE_PATH.formatted(
        timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME));

    try {

      saveStringAsJsonFile(Constants.HERMES_DATA_BUCKET_NAME, filePath, linkWithGeoEvents);
      log.info("{} - Succeed to archive data in S3 bucket {}",
          Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, Constants.HERMES_DATA_BUCKET_NAME);

    } catch (Exception e) {

      storeEventsToLocalFiles(Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, filePath,
          linkWithGeoEvents);
      log.error("{} - Failed to archive data in S3 bucket {}: {}",
          Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, Constants.HERMES_DATA_BUCKET_NAME,
          e.getMessage(), e);

    }
  }

  public void archiveRouteEvents(@NonNull OffsetDateTime timestamp, String routeEvents) {

    String filePath = Constants.ROUTES_FILE_PATH.formatted(
        timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME));

    try {

      saveStringAsJsonFile(Constants.HERMES_DATA_BUCKET_NAME, filePath, routeEvents);
      log.info("{} - Succeed to archive data in S3 bucket {}",
          Constants.BLUETOOTH_DATA_TOPIC_ROUTES, Constants.HERMES_DATA_BUCKET_NAME);

    } catch (Exception e) {

      storeEventsToLocalFiles(Constants.BLUETOOTH_DATA_TOPIC_ROUTES, filePath, routeEvents);
      log.error("{} - Failed to archive data in S3 bucket {}: {}",
          Constants.BLUETOOTH_DATA_TOPIC_ROUTES, Constants.HERMES_DATA_BUCKET_NAME, e.getMessage(),
          e);

    }
  }

  @Override
  public void archiveSiteEvents(@NonNull OffsetDateTime timestamp, String siteEvents) {

    String filePath = Constants.SITES_FILE_PATH.formatted(
        timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME));

    try {

      saveStringAsJsonFile(Constants.HERMES_DATA_BUCKET_NAME, filePath, siteEvents);
      log.info("{} - Succeed to archive data in S3 bucket {}",
          Constants.BLUETOOTH_DATA_TOPIC_SITES, Constants.HERMES_DATA_BUCKET_NAME);

    } catch (Exception e) {

      storeEventsToLocalFiles(Constants.BLUETOOTH_DATA_TOPIC_SITES, filePath, siteEvents);
      log.error("{} - Failed to archive data in S3 bucket {}: {}",
          Constants.BLUETOOTH_DATA_TOPIC_SITES, Constants.HERMES_DATA_BUCKET_NAME,
          e.getMessage(), e);

    }
  }

  public void saveStringAsJsonFile(String bucketName, String objectKey,
      String jsonString) {

    PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(objectKey)
        .contentType("application/json").build();

    RequestBody requestBody = RequestBody.fromString(jsonString, StandardCharsets.UTF_8);
    s3Client.putObject(putObjectRequest, requestBody);
  }

  private void storeEventsToLocalFiles(String topic, String filePath, String content) {

    Path targetPath = Paths.get(filePath);

    try {
      Files.createDirectories(targetPath.getParent());
      Files.writeString(targetPath, content);
    } catch (IOException e) {
      log.error("{} - Failed to archive data locally: {}", topic, e.getMessage(), e);
    }
  }
}
