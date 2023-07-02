package org.bugmakers404.hermes.producer.vicroad.service.interfaces;

import java.time.OffsetDateTime;
import lombok.NonNull;

public interface EventsArchiveService {

  void archiveEvents(@NonNull String topic, @NonNull OffsetDateTime timestamp, String events);

}
