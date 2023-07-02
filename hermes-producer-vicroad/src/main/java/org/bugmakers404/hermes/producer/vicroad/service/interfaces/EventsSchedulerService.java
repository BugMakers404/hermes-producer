package org.bugmakers404.hermes.producer.vicroad.service.interfaces;

import java.io.IOException;

public interface EventsSchedulerService {

  void collectAndSendLinksData() throws IOException;

  void collectAndSendLinksWithGeoData();

  void collectAndSendRoutesData();

  void collectAndSendRoutesWithGeoData();

  void collectAndSendSitesData();

  void collectAndSendSitesWithGeoData();
}
