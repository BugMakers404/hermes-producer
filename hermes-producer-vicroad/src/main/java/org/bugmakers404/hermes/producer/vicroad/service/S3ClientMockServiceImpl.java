package org.bugmakers404.hermes.producer.vicroad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsArchiveService;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
@Profile("!prod")
@RequiredArgsConstructor
public class S3ClientMockServiceImpl implements EventsArchiveService {

    @Override
    public void archiveLinkEvents(OffsetDateTime timestamp, String linkEvents) {
        log.info("{} - Succeed to archive data in S3 bucket {}",
                Constants.BLUETOOTH_DATA_TOPIC_LINKS, Constants.HERMES_DATA_BUCKET_NAME);
  }

  @Override
  public void archiveLinkWithGeoEvents(OffsetDateTime timestamp, String linkWithGeoEvents) {
      log.info("{} - Succeed to archive data in S3 bucket {}",
              Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, Constants.HERMES_DATA_BUCKET_NAME);
  }

  @Override
  public void archiveRouteEvents(OffsetDateTime timestamp, String routeEvents) {
      log.info("{} - Succeed to archive data in S3 bucket {}",
              Constants.BLUETOOTH_DATA_TOPIC_ROUTES, Constants.HERMES_DATA_BUCKET_NAME);
  }

  @Override
  public void archiveSiteEvents(OffsetDateTime timestamp, String siteEvents) {
      log.info("{} - Succeed to archive data in S3 bucket {}",
              Constants.BLUETOOTH_DATA_TOPIC_SITES, Constants.HERMES_DATA_BUCKET_NAME);
  }
}
