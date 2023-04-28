package org.bugmakers404.hermes.producer.vicroad.task;

import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.BLUETOOTH_DATA_TOPIC_LINKS;
import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO;
import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.BLUETOOTH_DATA_TOPIC_ROUTES;
import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.BLUETOOTH_DATA_TOPIC_SITES;
import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.DATE_TIME_FORMATTER_FOR_KAFKA;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaEventsProducer {

  public final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

  public void sendLinkEvents(ZonedDateTime timestamp, String linkEvents) throws IOException {
    sendProducerRecords(BLUETOOTH_DATA_TOPIC_LINKS, timestamp.format(DATE_TIME_FORMATTER_FOR_KAFKA), linkEvents);
  }

  public void sendLinkWithGeoEvents(ZonedDateTime timestamp, String linkWithGeoEvents) throws IOException {
    sendProducerRecords(BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, timestamp.format(DATE_TIME_FORMATTER_FOR_KAFKA),
        linkWithGeoEvents);
  }

  public void sendRouteEvents(ZonedDateTime timestamp, String routeEvents) throws IOException {
    sendProducerRecords(BLUETOOTH_DATA_TOPIC_ROUTES, timestamp.format(DATE_TIME_FORMATTER_FOR_KAFKA), routeEvents);
  }

  public void sendSiteEvents(ZonedDateTime timestamp, String siteEvents) throws IOException {
    sendProducerRecords(BLUETOOTH_DATA_TOPIC_SITES, timestamp.format(DATE_TIME_FORMATTER_FOR_KAFKA), siteEvents);
  }

  private void sendProducerRecords(String topic, String timestamp, String events) throws IOException {
    List<Map<String, Object>> eventsList = objectMapper.readValue(events, new TypeReference<>() {
    });
    for (Map<String, Object> event : eventsList) {
      ProducerRecord<String, String> eventRecord = buildProducerRecord(topic, event, timestamp);
      try {
        kafkaTemplate.send(eventRecord).get();
      } catch (Exception e) {
        if (e.getCause() instanceof KafkaProducerException producerException) {
          ProducerRecord<?, ?> failedRecord = producerException.getFailedProducerRecord();
          log.error("Failed to send message with key={} to topic={} due to: {}", failedRecord.key(),
              failedRecord.topic(), producerException.getMessage(), producerException);
        } else {
          log.error("Failed to send message with key={} to topic={} due to: {}", eventRecord.key(), eventRecord.topic(),
              e.getMessage(), e);
        }
      }
    }
    log.info("{} - succeed to send data.", topic);
  }

  private ProducerRecord<String, String> buildProducerRecord(String topic, Map<String, Object> event, String timestamp)
      throws JsonProcessingException {
    String key = Constants.EVENT_RECORD_KEY_TEMPLATE.formatted(timestamp, (Integer) event.get("id"));
    return new ProducerRecord<>(topic, null, key, objectMapper.writeValueAsString(event));
  }

}
