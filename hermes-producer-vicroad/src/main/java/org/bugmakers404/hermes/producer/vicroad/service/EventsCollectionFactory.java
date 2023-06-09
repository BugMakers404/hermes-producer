package org.bugmakers404.hermes.producer.vicroad.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.bugmakers404.hermes.producer.vicroad.configs.EventsCollector;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsCollectionService;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventsCollectionFactory implements EventsCollectionService {

  private final EventsCollector linksCollector;

  private final EventsCollector linksWithGeometryCollector;

  private final EventsCollector routesCollector;

  private final EventsCollector sitesCollector;

  public EventsCollectionFactory(
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
  public HttpResponse fetchLinksData() {
    try {
      return this.linksCollector.fetchData();
    } catch (Exception e) {
        log.error("{} - Failed to fetch data from the platform: {}",
                Constants.BLUETOOTH_DATA_TOPIC_LINKS, e.getMessage(), e);
      return getAFailedResponse();
    }
  }

  @Override
  public HttpResponse fetchLinksWithGeoData() {
    try {
      return this.linksWithGeometryCollector.fetchData();
    } catch (Exception e) {
        log.error("{} - Failed to fetch data from the platform: {}",
                Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, e.getMessage(), e);
      return getAFailedResponse();
    }
  }

  @Override
  public HttpResponse fetchRoutesData() {
    try {
      return this.routesCollector.fetchData();
    } catch (Exception e) {
        log.error("{} - Failed to fetch data from the platform: {}",
                Constants.BLUETOOTH_DATA_TOPIC_ROUTES, e.getMessage(), e);
      return getAFailedResponse();
    }
  }

  @Override
  public HttpResponse fetchSitesData() {
    try {
      return this.sitesCollector.fetchData();
    } catch (Exception e) {
        log.error("{} - Failed to fetch data from the platform: {}",
                Constants.BLUETOOTH_DATA_TOPIC_SITES, e.getMessage(), e);
      return getAFailedResponse();
    }
  }

  public HttpResponse getAFailedResponse() {
      BasicStatusLine statusLine = new BasicStatusLine(HttpVersion.HTTP_1_1, 404,
              "Failed to get response from the remote");
    return new BasicHttpResponse(statusLine);
  }
}
