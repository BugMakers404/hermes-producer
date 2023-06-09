package org.bugmakers404.hermes.producer.vicroad.service.interfaces;

import java.time.OffsetDateTime;

public interface EventsSendingService {

    void sendLinkEvents(OffsetDateTime timestamp, String linkEvents);

  void sendLinkWithGeoEvents(OffsetDateTime timestamp, String linkWithGeoEvents);

  void sendRouteEvents(OffsetDateTime timestamp, String routeEvents);

  void sendSiteEvents(OffsetDateTime timestamp, String siteEvents);

}
