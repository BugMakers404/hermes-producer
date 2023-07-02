package org.bugmakers404.hermes.producer.vicroad.service;

import java.time.OffsetDateTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsArchiveService;
import org.bugmakers404.hermes.producer.vicroad.util.Constants;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("!prod")
@RequiredArgsConstructor
public class S3ClientMockServiceImpl implements EventsArchiveService {

  @Override
  public void archiveEvents(@NonNull String topic, @NonNull OffsetDateTime timestamp,
      String events) {
    log.info("{} - Succeed to archive data at {} in S3 bucket {}", topic, timestamp,
        Constants.HERMES_DATA_BUCKET_NAME);
  }
}
