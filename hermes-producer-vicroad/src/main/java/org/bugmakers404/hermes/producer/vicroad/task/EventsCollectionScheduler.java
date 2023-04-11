package org.bugmakers404.hermes.producer.vicroad.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableAsync
@Configuration
@Slf4j
public class EventsCollectionScheduler {

  private final EventsCollector linksCollector;

  private final EventsCollector linksWithGeometryCollector;

  private final EventsCollector routesCollector;

  private final EventsCollector sitesCollector;

  private final KafkaEventsProducer kafkaEventsProducer;

  public EventsCollectionScheduler(@NonNull @Qualifier("linksCollector") EventsCollector linksCollector,
      @NonNull @Qualifier("linksWithGeometryCollector") EventsCollector linksWithGeometryCollector,
      @NonNull @Qualifier("routesCollector") EventsCollector routesCollector,
      @NonNull @Qualifier("sitesCollector") EventsCollector sitesCollector, KafkaEventsProducer kafkaEventsProducer) {
    this.linksCollector = linksCollector;
    this.linksWithGeometryCollector = linksWithGeometryCollector;
    this.routesCollector = routesCollector;
    this.sitesCollector = sitesCollector;
    this.kafkaEventsProducer = kafkaEventsProducer;
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DATA_DURATION)
  public void collectLinksData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Link - start data collection.");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = linksCollector.fetchData();
      entity = response.getEntity();
      if (response.getStatusLine().getStatusCode() == 200) {
        String content = EntityUtils.toString(entity);

        storeEventsToLocalFiles(content, Constants.LINKS_FILE_PATH.formatted(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN_FOR_FILENAME))));

        kafkaEventsProducer.sendLinkEvents(content);

        log.info("Link - successes to archive data.");
        return;
      } else {
        retries++;
        log.warn("Link - failed to collect data. Retrying...");
      }
    }

    log.error("Link - Failed to collect data after %d retries!!!".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DATA_DURATION)
  public void collectLinksWithGeometryData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Link with Geo - start data collection.");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = linksWithGeometryCollector.fetchData();
      entity = response.getEntity();

      if (response.getStatusLine().getStatusCode() == 200) {
        String content = EntityUtils.toString(entity);

        storeEventsToLocalFiles(content, Constants.LINKS_WITH_GEO_FILE_PATH.formatted(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN_FOR_FILENAME))));

        kafkaEventsProducer.sendLinkWithGeoEvents(content);

        log.info("Link with Geo - successes to archive data.");
        return;
      } else {
        retries++;
        log.warn("Link with Geo - failed to collect data. Retrying...");
      }
    }

    log.error(
        "Link with Geo - failed to collect data after %d retries!!!".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DATA_DURATION)
  public void collectRoutesData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Route - start data collection.");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = routesCollector.fetchData();
      entity = response.getEntity();

      if (response.getStatusLine().getStatusCode() == 200) {
        String content = EntityUtils.toString(entity);

        storeEventsToLocalFiles(content, Constants.ROUTES_FILE_PATH.formatted(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN_FOR_FILENAME))));

        kafkaEventsProducer.sendRouteEvents(content);

        log.info("Route - successes to archive data.");
        return;
      } else {
        retries++;
        log.warn("Route - failed to collect data. Retrying...");
      }
    }

    log.error("Route - failed to collect data after %d retries!!!".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DATA_DURATION)
  public void collectSitesData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Site - start data collection.");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = sitesCollector.fetchData();
      entity = response.getEntity();

      if (response.getStatusLine().getStatusCode() == 200) {
        String content = EntityUtils.toString(entity);

        storeEventsToLocalFiles(content, Constants.SITES_FILE_PATH.formatted(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN_FOR_FILENAME))));

        kafkaEventsProducer.sendSiteEvents(content);

        log.info("Site - successes to archive data.");
        return;
      } else {
        retries++;
        log.warn("Site - failed to collect data. Retrying...");
      }
    }

    log.error("Site - failed to collect data after %d retries!!!".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

  public void storeEventsToLocalFiles(String content, String filePath) {
    Path targetPath = Paths.get(filePath);
    try {
      Files.createDirectories(targetPath.getParent());
      Files.writeString(targetPath, content);
    } catch (IOException e) {
      throw new RuntimeException("Failed to store string data to a local file!!!", e);
    }
  }

}
