package org.bugmakers404.hermes.producer.vicroad.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableAsync
@Configuration
@Slf4j
@RequiredArgsConstructor
public class EventsCollectionScheduler {

  private final AggregatedEventsCollector aggregatedEventsCollector;

  private final KafkaEventsProducer kafkaEventsProducer;

  private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

  @Async
  @Scheduled(fixedRate = Constants.BASIC_BLUETOOTH_DATA_DURATION)
  public void collectLinksData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Link - start data collection.");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = aggregatedEventsCollector.fetchLinksData();
      if (response.getStatusLine().getStatusCode() == 200) {
        entity = response.getEntity();
        String content = EntityUtils.toString(entity);

        ZonedDateTime timestamp = extractTimestampFromContent(content);
        storeEventsToLocalFiles(content,
            Constants.LINKS_FILE_PATH.formatted(timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME)));

        kafkaEventsProducer.sendLinkEvents(timestamp, content);

        log.info("Link - succeed to archive data.");
        return;
      } else {
        retries++;
        log.warn("Link - failed to collect data. Retrying...");
      }
    }

    log.error("Link - Failed to collect data after %d retries!!!".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

  @Async
  @Scheduled(cron = "0 0 3 * * ?")
  public void collectLinksWithGeoData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Link with Geo - start data collection.");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = aggregatedEventsCollector.fetchLinksWithGeoData();
      if (response.getStatusLine().getStatusCode() == 200) {
        entity = response.getEntity();
        String content = EntityUtils.toString(entity);

        ZonedDateTime timestamp = extractTimestampFromContent(content);
        storeEventsToLocalFiles(content,
            Constants.LINKS_WITH_GEO_FILE_PATH.formatted(timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME)));

        kafkaEventsProducer.sendLinkWithGeoEvents(timestamp, content);

        log.info("Link with Geo - succeed to archive data.");
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
  @Scheduled(fixedRate = Constants.BASIC_BLUETOOTH_DATA_DURATION)
  public void collectRoutesData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Route - start data collection.");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = aggregatedEventsCollector.fetchRoutesData();
      if (response.getStatusLine().getStatusCode() == 200) {
        entity = response.getEntity();
        String content = EntityUtils.toString(entity);

        ZonedDateTime timestamp = extractTimestampFromContent(content);
        storeEventsToLocalFiles(content,
            Constants.ROUTES_FILE_PATH.formatted(timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME)));

        kafkaEventsProducer.sendRouteEvents(timestamp, content);

        log.info("Route - succeed to archive data.");
        return;
      } else {
        retries++;
        log.warn("Route - failed to collect data. Retrying...");
      }
    }

    log.error("Route - failed to collect data after %d retries!!!".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DATA_SITE_DURATION)
  public void collectSitesData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Site - start data collection.");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = aggregatedEventsCollector.fetchSitesData();
      if (response.getStatusLine().getStatusCode() == 200) {
        entity = response.getEntity();
        String content = EntityUtils.toString(entity);

        ZonedDateTime timestamp = extractTimestampFromContent(content);
        storeEventsToLocalFiles(content,
            Constants.SITES_FILE_PATH.formatted(timestamp.format(Constants.DATE_TIME_FORMATTER_FOR_FILENAME)));

        kafkaEventsProducer.sendSiteEvents(timestamp, content);

        log.info("Site - succeed to archive data.");
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

  private ZonedDateTime extractTimestampFromContent(String events) {
    JsonArray jsonArray = JsonParser.parseString(events).getAsJsonArray();

    for (JsonElement jsonElement : jsonArray) {
      JsonObject jsonObject = jsonElement.getAsJsonObject();
      JsonObject latestStats = jsonObject.getAsJsonObject("latest_stats");
      String intervalStart = latestStats.get("interval_start").getAsString();
      if (!intervalStart.isEmpty()) {
        return ZonedDateTime.parse(intervalStart);
      }
    }

    return ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
  }

}
