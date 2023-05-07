package org.bugmakers404.hermes.producer.vicroad.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsArchiveService;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class EventsScheduler {

  private final EventsCollectionFactory eventsCollectionFactory;

  private final KafkaProducerServiceImpl kafkaProducerServiceImpl;

  private final EventsArchiveService s3Archiver;

  private final JsonFactory jsonFactory = new JsonFactory();

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DEFAULT_COLLECTION_INTERVAL)
  public void collectLinksData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("{} - start data collection.", Constants.BLUETOOTH_DATA_TOPIC_LINKS);

    while (retries < Constants.BLUETOOTH_MAX_COLLECTION_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = eventsCollectionFactory.fetchLinksData();
      if (response.getStatusLine().getStatusCode() == 200) {
        entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        OffsetDateTime timestamp = extractTimestampFromEvents(content);

        kafkaProducerServiceImpl.sendLinkEvents(timestamp, content);
        s3Archiver.archiveLinkEvents(timestamp, content);
        return;
      } else {
        retries++;
        log.warn("{} - failed to collect data. Retrying...", Constants.BLUETOOTH_DATA_TOPIC_LINKS);
      }
    }

    log.error("{} - Failed to collect data after {} retries!!!",
        Constants.BLUETOOTH_DATA_TOPIC_LINKS, Constants.BLUETOOTH_MAX_COLLECTION_RETRIES);
  }

  @Async
  @Scheduled(cron = "0 0 3 * * ?")
  public void collectLinksWithGeoData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("{} - start data collection.", Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO);

    while (retries < Constants.BLUETOOTH_MAX_COLLECTION_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = eventsCollectionFactory.fetchLinksWithGeoData();
      if (response.getStatusLine().getStatusCode() == 200) {
        entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        OffsetDateTime timestamp = extractTimestampFromEvents(content);

        kafkaProducerServiceImpl.sendLinkWithGeoEvents(timestamp, content);
        s3Archiver.archiveLinkWithGeoEvents(timestamp, content);
        return;
      } else {
        retries++;
        log.warn("{} - failed to collect data. Retrying...",
            Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO);
      }
    }

    log.error("{} - failed to collect data after {} retries!!!",
        Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, Constants.BLUETOOTH_MAX_COLLECTION_RETRIES);
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DEFAULT_COLLECTION_INTERVAL)
  public void collectRoutesData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("{} - start data collection.", Constants.BLUETOOTH_DATA_TOPIC_ROUTES);

    while (retries < Constants.BLUETOOTH_MAX_COLLECTION_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = eventsCollectionFactory.fetchRoutesData();
      if (response.getStatusLine().getStatusCode() == 200) {
        entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        OffsetDateTime timestamp = extractTimestampFromEvents(content);

        kafkaProducerServiceImpl.sendRouteEvents(timestamp, content);
        s3Archiver.archiveRouteEvents(timestamp, content);
        return;
      } else {
        retries++;
        log.warn("{} - failed to collect data. Retrying...", Constants.BLUETOOTH_DATA_TOPIC_ROUTES);
      }
    }

    log.error("{} - failed to collect data after {} retries!!!",
        Constants.BLUETOOTH_DATA_TOPIC_ROUTES, Constants.BLUETOOTH_MAX_COLLECTION_RETRIES);
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_SITE_COLLECTION_INTERVAL)
  public void collectSitesData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("{} - start data collection.", Constants.BLUETOOTH_DATA_TOPIC_SITES);

    while (retries < Constants.BLUETOOTH_MAX_COLLECTION_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = eventsCollectionFactory.fetchSitesData();
      if (response.getStatusLine().getStatusCode() == 200) {
        entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        OffsetDateTime timestamp = extractTimestampFromEvents(content);

        kafkaProducerServiceImpl.sendSiteEvents(timestamp, content);
        s3Archiver.archiveSiteEvents(timestamp, content);
        return;
      } else {
        retries++;
        log.warn("{} - failed to collect data. Retrying...", Constants.BLUETOOTH_DATA_TOPIC_SITES);
      }
    }

    log.error("{} - failed to collect data after {} retries!!!",
        Constants.BLUETOOTH_DATA_TOPIC_SITES, Constants.BLUETOOTH_MAX_COLLECTION_RETRIES);
  }

  private OffsetDateTime extractTimestampFromEvents(String events) {
    try (JsonParser parser = jsonFactory.createParser(events)) {
      JsonToken token;
      while ((token = parser.nextToken()) != null) {
        if (token == JsonToken.FIELD_NAME && "interval_start".equals(parser.getCurrentName())) {
          parser.nextToken(); // Move to the value of the specified field
          String fieldValue = parser.getValueAsString();
          if (fieldValue != null && !fieldValue.isEmpty()) {
            return OffsetDateTime.parse(fieldValue);
          }
        }
      }
    } catch (IOException e) {
      log.error("Failed to extract timestamp from JSON events: {}", e.getMessage(), e);
    }

    return OffsetDateTime.now();
  }
}
