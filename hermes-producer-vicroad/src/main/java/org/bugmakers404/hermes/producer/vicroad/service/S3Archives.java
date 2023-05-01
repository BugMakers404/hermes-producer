package org.bugmakers404.hermes.producer.vicroad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Archives {

    private final S3Client s3Client;

    public PutObjectResponse saveStringAsJsonFile(String bucketName, String objectKey, String jsonString) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(objectKey).contentType("application/json").build();

        RequestBody requestBody = RequestBody.fromString(jsonString, StandardCharsets.UTF_8);

        return s3Client.putObject(putObjectRequest, requestBody);
    }

    public void archiveLinkEvents(OffsetDateTime timestamp, String linkEvents) {
        String filePath = Constants.LINKS_FILE_PATH.formatted(timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME));
        try {
            saveStringAsJsonFile(Constants.HERMES_DATA_BUCKET_NAME, filePath, linkEvents);
        } catch (Exception e) {
            storeEventsToLocalFiles(Constants.BLUETOOTH_DATA_TOPIC_LINKS, filePath, linkEvents);
            log.error("{} - Failed to archive data in S3 bucket {}: {}", Constants.BLUETOOTH_DATA_TOPIC_LINKS, Constants.HERMES_DATA_BUCKET_NAME, e.getMessage(), e);
        }
    }

    public void archiveLinkWithGeoEvents(OffsetDateTime timestamp, String linkWithGeoEvents) {
        String filePath = Constants.LINKS_WITH_GEO_FILE_PATH.formatted(timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME));
        try {
            saveStringAsJsonFile(Constants.HERMES_DATA_BUCKET_NAME, filePath, linkWithGeoEvents);
        } catch (Exception e) {
            storeEventsToLocalFiles(Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, filePath, linkWithGeoEvents);
            log.error("{} - Failed to archive data in S3 bucket {}: {}", Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, Constants.HERMES_DATA_BUCKET_NAME, e.getMessage(), e);
        }
    }

    public void archiveRouteEvents(OffsetDateTime timestamp, String routeEvents) {
        String filePath = Constants.ROUTES_FILE_PATH.formatted(timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME));
        try {
            saveStringAsJsonFile(Constants.HERMES_DATA_BUCKET_NAME, filePath, routeEvents);
        } catch (Exception e) {
            storeEventsToLocalFiles(Constants.BLUETOOTH_DATA_TOPIC_ROUTES, filePath, routeEvents);
            log.error("{} - Failed to archive data in S3 bucket {}: {}", Constants.BLUETOOTH_DATA_TOPIC_ROUTES, Constants.HERMES_DATA_BUCKET_NAME, e.getMessage(), e);
        }
    }

    public void archiveSiteEvents(OffsetDateTime timestamp, String siteEvents) {
        String filePath = Constants.SITES_FILE_PATH.formatted(timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME));
        try {
            saveStringAsJsonFile(Constants.HERMES_DATA_BUCKET_NAME, filePath, siteEvents);
        } catch (Exception e) {
            storeEventsToLocalFiles(Constants.BLUETOOTH_DATA_TOPIC_SITES, filePath, siteEvents);
            log.error("{} - Failed to archive data in S3 bucket {}: {}", Constants.BLUETOOTH_DATA_TOPIC_SITES, Constants.HERMES_DATA_BUCKET_NAME, e.getMessage(), e);
        }
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
