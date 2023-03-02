package org.bugmakers404.hermes.vicroad.task;

import java.io.IOException;
import java.time.LocalDateTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Links;
import org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces.LinksService;
import org.bugmakers404.hermes.vicroad.utils.CONSTANTS;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableAsync
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataCollectionScheduler {
  @NonNull
  private final BluetoothTravelTimeCollector bluetoothTravelTimeCollector;

  @NonNull
  private final LinksService linksService;

  @Scheduled(fixedRate = CONSTANTS.BLUETOOTH_DATA_DURATION)
  public void collectBluetoothLinksData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Starting data collection of links");

    while (retries < CONSTANTS.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < CONSTANTS.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = bluetoothTravelTimeCollector.fetchData();
      entity = response.getEntity();

      if (response.getStatusLine().getStatusCode() == 200) {
        log.info("Links data are successfully received");
        Links collectedLinks = new Links(LocalDateTime.now().toString(), EntityUtils.toString(entity));
        linksService.saveNewLinks(collectedLinks);
        log.info("Links data collected and stored successfully");
        return;
      } else {
        retries++;
        log.warn("Failed to collect links data. Retrying...");
      }
    }

    log.error("Failed to collect links data after %d retries".formatted(CONSTANTS.BLUETOOTH_DATA_MAX_RETRIES));
  }
}
