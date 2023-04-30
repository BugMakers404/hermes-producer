package org.bugmakers404.hermes.producer.vicroad.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.bugmakers404.hermes.producer.vicroad.configs.EventsCollector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AggregatedEventsCollector {

  private final EventsCollector linksCollector;

  private final EventsCollector linksWithGeometryCollector;

  private final EventsCollector routesCollector;

  private final EventsCollector sitesCollector;

  public AggregatedEventsCollector(@NonNull @Qualifier("linksCollector") EventsCollector linksCollector,
                                   @NonNull @Qualifier("linksWithGeometryCollector") EventsCollector linksWithGeometryCollector,
                                   @NonNull @Qualifier("routesCollector") EventsCollector routesCollector,
                                   @NonNull @Qualifier("sitesCollector") EventsCollector sitesCollector) {

    this.linksCollector = linksCollector;
    this.linksWithGeometryCollector = linksWithGeometryCollector;
    this.routesCollector = routesCollector;
    this.sitesCollector = sitesCollector;
  }

  public HttpResponse fetchLinksData() throws IOException {
    return this.linksCollector.fetchData();
  }

  public HttpResponse fetchLinksWithGeoData() throws IOException {
    return this.linksWithGeometryCollector.fetchData();
  }

  public HttpResponse fetchRoutesData() throws IOException {
    return this.routesCollector.fetchData();
  }

  public HttpResponse fetchSitesData() throws IOException {
    return this.sitesCollector.fetchData();
  }
}
