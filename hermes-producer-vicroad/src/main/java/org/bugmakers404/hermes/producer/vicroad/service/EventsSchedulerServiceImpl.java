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
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsCollectionService;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsSchedulerService;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsSendingService;
import org.bugmakers404.hermes.producer.vicroad.util.Constants;
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
public class EventsSchedulerServiceImpl implements EventsSchedulerService {

  private final EventsCollectionService eventsCollectionService;

  private final EventsSendingService eventsSendingService;

  private final EventsArchiveService s3Archiver;

  private final JsonFactory jsonFactory = new JsonFactory();

  @Override
  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DEFAULT_COLLECTION_INTERVAL)
  public void collectAndSendLinksData() {
    this.collectAndSendToKafkaBroker(Constants.BLUETOOTH_DATA_TOPIC_LINKS);
  }

  @Override
  @Async
  @Scheduled(cron = Constants.BLUETOOTH_LINK_WITH_GEO_DATA_COLLECTION_TIME)
  public void collectAndSendLinksWithGeoData() {
    this.collectAndSendToKafkaBroker(Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO);
  }

  @Override
  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DEFAULT_COLLECTION_INTERVAL)
  public void collectAndSendRoutesData() {
    this.collectAndSendToKafkaBroker(Constants.BLUETOOTH_DATA_TOPIC_ROUTES);
  }

  @Override
  @Async
  @Scheduled(cron = Constants.BLUETOOTH_ROUTE_WITH_GEO_DATA_COLLECTION_TIME)
  public void collectAndSendRoutesWithGeoData() {
    this.collectAndSendToKafkaBroker(Constants.BLUETOOTH_DATA_TOPIC_ROUTES_WITH_GEO);
  }

  @Override
  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_SITE_COLLECTION_INTERVAL)
  public void collectAndSendSitesData() {
    this.collectAndSendToKafkaBroker(Constants.BLUETOOTH_DATA_TOPIC_SITES);
  }

  @Override
  @Async
  @Scheduled(cron = Constants.BLUETOOTH_SITE_WITH_GEO_DATA_COLLECTION_TIME)
  public void collectAndSendSitesWithGeoData() {
    this.collectAndSendToKafkaBroker(Constants.BLUETOOTH_DATA_TOPIC_SITES_WITH_GEO);
  }

  private void collectAndSendToKafkaBroker(String topic) {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("{} - start data collection.", topic);

    while (retries < Constants.BLUETOOTH_MAX_COLLECTION_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = eventsCollectionService.fetchData(topic);
      if (response.getStatusLine().getStatusCode() == 200) {

        try {
          entity = response.getEntity();
          String content = EntityUtils.toString(entity);
          OffsetDateTime timestamp = extractTimestampFromEvents(content);

          eventsSendingService.sendProducerRecords(topic, timestamp, content);
          s3Archiver.archiveEvents(topic, timestamp, content);
          return;
        } catch (Exception e) {
          log.error("{} - failed to parse the response entity: {}", topic, e.getMessage(), e);
        }

      } else {
        log.warn("{} - failed to collect data. Retrying...", topic);
      }
      retries++;
    }

    log.error("{} - failed to collect data after {} retries!!!",
        topic, Constants.BLUETOOTH_MAX_COLLECTION_RETRIES);
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
