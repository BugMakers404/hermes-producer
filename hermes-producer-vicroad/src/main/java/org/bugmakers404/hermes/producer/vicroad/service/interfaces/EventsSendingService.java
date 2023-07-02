package org.bugmakers404.hermes.producer.vicroad.service.interfaces;

import java.time.OffsetDateTime;

public interface EventsSendingService {

  void sendProducerRecords(String topic, OffsetDateTime timestamp, String events);

}
