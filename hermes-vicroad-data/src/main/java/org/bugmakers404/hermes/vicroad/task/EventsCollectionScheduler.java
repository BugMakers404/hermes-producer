package org.bugmakers404.hermes.vicroad.task;

import java.io.IOException;
import java.time.LocalDateTime;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.LinksWithGeometry;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Routes;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Sites;
import org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces.LinksService;
import org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces.LinksWithGeometryService;
import org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces.RoutesService;
import org.bugmakers404.hermes.vicroad.service.bluetoothRawData.Interfaces.SitesService;
import org.bugmakers404.hermes.vicroad.strategies.BluetoothEventsPreprocessor;
import org.bugmakers404.hermes.vicroad.strategies.EventsCollector;
import org.bugmakers404.hermes.vicroad.utils.Constants;
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

  private final LinksService linksService;

  private final LinksWithGeometryService linksWithGeometryService;

  private final RoutesService routesService;

  private final SitesService sitesService;

  private final BluetoothEventsPreprocessor eventsPreprocessor;

  private final KafkaEventsProducer kafkaEventsProducer;

  public EventsCollectionScheduler(@NonNull @Qualifier("linksCollector") EventsCollector linksCollector,
      @NonNull @Qualifier("linksWithGeometryCollector") EventsCollector linksWithGeometryCollector,
      @NonNull @Qualifier("routesCollector") EventsCollector routesCollector,
      @NonNull @Qualifier("sitesCollector") EventsCollector sitesCollector, @NonNull LinksService linksService,
      @NonNull LinksWithGeometryService linksWithGeometryService, @NonNull RoutesService routesService,
      @NonNull SitesService sitesService, BluetoothEventsPreprocessor eventsPreprocessor,
      KafkaEventsProducer kafkaEventsProducer) {
    this.linksCollector = linksCollector;
    this.linksWithGeometryCollector = linksWithGeometryCollector;
    this.routesCollector = routesCollector;
    this.sitesCollector = sitesCollector;
    this.linksService = linksService;
    this.linksWithGeometryService = linksWithGeometryService;
    this.routesService = routesService;
    this.sitesService = sitesService;
    this.eventsPreprocessor = eventsPreprocessor;
    this.kafkaEventsProducer = kafkaEventsProducer;
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DATA_DURATION)
  public void collectLinksData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Starting data collection of links");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = linksCollector.fetchData();
      entity = response.getEntity();

      if (response.getStatusLine().getStatusCode() == 200) {
        log.info("Links data are successfully received");

        eventsPreprocessor.filterLinksForKafka(EntityUtils.toString(entity));
        kafkaEventsProducer.sendLinksEvent(linkEventsForKafka);
        log.info("Links data are stored and sent successfully");
        return;
      } else {
        retries++;
        log.warn("Failed to collect links data. Retrying...");
      }
    }

    log.error("Failed to collect links data after %d retries".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DATA_DURATION)
  public void collectLinksWithGeometryData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Starting data collection of links with geometry");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = linksWithGeometryCollector.fetchData();
      entity = response.getEntity();

      if (response.getStatusLine().getStatusCode() == 200) {
        log.info("Links with geometry data are successfully received");
        LinksWithGeometry collectedLinksWithGeometry = new LinksWithGeometry(LocalDateTime.now().toString(),
            EntityUtils.toString(entity));
        linksWithGeometryService.saveNewLinksWithGeometry(collectedLinksWithGeometry);
        log.info("Links with geometry data collected and stored successfully");
        return;
      } else {
        retries++;
        log.warn("Failed to collect links with geometry data. Retrying...");
      }
    }

    log.error(
        "Failed to collect links with geometry data after %d retries".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DATA_DURATION)
  public void collectRoutesData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Starting data collection of routes");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = routesCollector.fetchData();
      entity = response.getEntity();

      if (response.getStatusLine().getStatusCode() == 200) {
        log.info("Routes data are successfully received");
        Routes collectedRoutes = new Routes(LocalDateTime.now().toString(), EntityUtils.toString(entity));
        routesService.saveNewRoutes(collectedRoutes);
        log.info("Routes data collected and stored successfully");
        return;
      } else {
        retries++;
        log.warn("Failed to collect routes data. Retrying...");
      }
    }

    log.error("Failed to collect routes data after %d retries".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

  @Async
  @Scheduled(fixedRate = Constants.BLUETOOTH_DATA_DURATION)
  public void collectSitesData() throws IOException {
    HttpEntity entity;
    int retries = 0;
    long startTime = System.currentTimeMillis();

    log.info("Starting data collection of sites");

    while (retries < Constants.BLUETOOTH_DATA_MAX_RETRIES
        && System.currentTimeMillis() - startTime < Constants.BLUETOOTH_DATA_TIMEOUT) {

      HttpResponse response = sitesCollector.fetchData();
      entity = response.getEntity();

      if (response.getStatusLine().getStatusCode() == 200) {
        log.info("Sites data are successfully received");
        Sites collectedSites = new Sites(LocalDateTime.now().toString(), EntityUtils.toString(entity));
        sitesService.saveNewSites(collectedSites);
        log.info("Sites data collected and stored successfully");
        return;
      } else {
        retries++;
        log.warn("Failed to collect sites data. Retrying...");
      }
    }

    log.error("Failed to collect sites data after %d retries".formatted(Constants.BLUETOOTH_DATA_MAX_RETRIES));
  }

}
