package org.bugmakers404.hermes.producer.vicroad.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.bugmakers404.hermes.producer.vicroad.config.EventsCollector;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsCollectionService;
import org.bugmakers404.hermes.producer.vicroad.util.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventsCollectionServiceImpl implements EventsCollectionService {

  private final EventsCollector linksCollector;

  private final EventsCollector linksWithGeometryCollector;

  private final EventsCollector routesCollector;

  private final EventsCollector sitesCollector;

  public EventsCollectionServiceImpl(
      @NonNull @Qualifier("linksCollector") EventsCollector linksCollector,
      @NonNull @Qualifier("linksWithGeometryCollector") EventsCollector linksWithGeometryCollector,
      @NonNull @Qualifier("routesCollector") EventsCollector routesCollector,
      @NonNull @Qualifier("sitesCollector") EventsCollector sitesCollector) {

    this.linksCollector = linksCollector;
    this.linksWithGeometryCollector = linksWithGeometryCollector;
    this.routesCollector = routesCollector;
    this.sitesCollector = sitesCollector;
  }

  @Override
  public HttpResponse fetchData(@NonNull String topic) {
    return switch (topic) {
      case Constants.BLUETOOTH_DATA_TOPIC_LINKS -> this.fetchLinksData();
      case Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO -> this.fetchLinksWithGeoData();
      case Constants.BLUETOOTH_DATA_TOPIC_ROUTES, Constants.BLUETOOTH_DATA_TOPIC_ROUTES_WITH_GEO ->
          this.fetchRoutesData();
      case Constants.BLUETOOTH_DATA_TOPIC_SITES, Constants.BLUETOOTH_DATA_TOPIC_SITES_WITH_GEO ->
          this.fetchSitesData();
      default -> throw new IllegalStateException("Unexpected value: " + topic);
    };
  }

  private HttpResponse fetchLinksData() {
    try {
      return this.linksCollector.fetchData();
    } catch (Exception e) {
      log.error("{} - Failed to fetch data from the platform: {}",
          Constants.BLUETOOTH_DATA_TOPIC_LINKS, e.getMessage(), e);
      return getAFailedResponse();
    }
  }

  private HttpResponse fetchLinksWithGeoData() {
    try {
      return this.linksWithGeometryCollector.fetchData();
    } catch (Exception e) {
      log.error("{} - Failed to fetch data from the platform: {}",
          Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, e.getMessage(), e);
      return getAFailedResponse();
    }
  }

  private HttpResponse fetchRoutesData() {
    try {
      return this.routesCollector.fetchData();
    } catch (Exception e) {
      log.error("{} - Failed to fetch data from the platform: {}",
          Constants.BLUETOOTH_DATA_TOPIC_ROUTES, e.getMessage(), e);
      return getAFailedResponse();
    }
  }

  private HttpResponse fetchSitesData() {
    try {
      return this.sitesCollector.fetchData();
    } catch (Exception e) {
      log.error("{} - Failed to fetch data from the platform: {}",
          Constants.BLUETOOTH_DATA_TOPIC_SITES, e.getMessage(), e);
      return getAFailedResponse();
    }
  }

  private HttpResponse getAFailedResponse() {
    BasicStatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1, 404,
        "Failed to get response from the remote");
    return new BasicHttpResponse(statusLine);
  }
}
