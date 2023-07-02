package org.bugmakers404.hermes.producer.vicroad.service;

import static org.bugmakers404.hermes.producer.vicroad.util.Constants.DATE_TIME_FORMATTER_FOR_KAFKA;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsSendingService;
import org.bugmakers404.hermes.producer.vicroad.util.Constants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements EventsSendingService {

  public final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

  @Override
  public void sendProducerRecords(String topic, OffsetDateTime timestamp, String events) {
    try (JsonParser parser = objectMapper.getFactory().createParser(events)) {
      JsonToken token;
      while ((token = parser.nextToken()) != null) {

        // Chunk an array of JSON strings
        if (token == JsonToken.START_OBJECT) {
          JsonNode eventNode = parser.readValueAsTree();
          int id = eventNode.get("id").asInt();
          String eventJsonString = objectMapper.writeValueAsString(eventNode);

          ProducerRecord<String, String> eventRecord = buildProducerRecord(topic, timestamp, id,
              eventJsonString);

          // Async send each event to Kafka cluster
          kafkaTemplate.send(eventRecord).whenComplete((result, ex) -> {
            if (ex != null) {
              log.error("Failed to send message with key={} to topic={} due to: {}",
                  eventRecord.key(), eventRecord.topic(), ex.getMessage(), ex);
            }
          });

        }
      }
    } catch (Exception e) {
      log.error("{} - Failed to parse the JSON events: {}", topic, e.getMessage(), e);
    }

    log.info("{} - Succeed to async send events to Kafka", topic);
  }

  private ProducerRecord<String, String> buildProducerRecord(String topic, OffsetDateTime timestamp,
      Integer id, String event) {
    String timestampString = timestamp.format(DATE_TIME_FORMATTER_FOR_KAFKA);
    String key = Constants.EVENT_RECORD_KEY_TEMPLATE.formatted(timestampString, id);
    return new ProducerRecord<>(topic, null, key, event);
  }
}
