package org.bugmakers404.hermes.producer.vicroad.service.interfaces;

import java.time.OffsetDateTime;

public interface EventsArchiveService {

    void archiveLinkEvents(OffsetDateTime timestamp, String linkEvents);

  void archiveLinkWithGeoEvents(OffsetDateTime timestamp, String linkWithGeoEvents);

  void archiveRouteEvents(OffsetDateTime timestamp, String routeEvents);

  void archiveSiteEvents(OffsetDateTime timestamp, String siteEvents);

}
