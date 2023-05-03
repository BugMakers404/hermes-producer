package org.bugmakers404.hermes.producer.vicroad.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventsProducer {

    public final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public void sendLinkEvents(OffsetDateTime timestamp, String linkEvents) {
        sendProducerRecords(BLUETOOTH_DATA_TOPIC_LINKS, timestamp.format(DATE_TIME_FORMATTER_FOR_KAFKA), linkEvents);
    }

    public void sendLinkWithGeoEvents(OffsetDateTime timestamp, String linkWithGeoEvents) {
        sendProducerRecords(BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, timestamp.format(DATE_TIME_FORMATTER_FOR_KAFKA), linkWithGeoEvents);
    }

    public void sendRouteEvents(OffsetDateTime timestamp, String routeEvents) {
        sendProducerRecords(BLUETOOTH_DATA_TOPIC_ROUTES, timestamp.format(DATE_TIME_FORMATTER_FOR_KAFKA), routeEvents);
    }

    public void sendSiteEvents(OffsetDateTime timestamp, String siteEvents) {
        sendProducerRecords(BLUETOOTH_DATA_TOPIC_SITES, timestamp.format(DATE_TIME_FORMATTER_FOR_KAFKA), siteEvents);
    }

    private void sendProducerRecords(String topic, String timestamp, String events) {

        try (JsonParser parser = objectMapper.getFactory().createParser(events)) {
            JsonToken token;
            while ((token = parser.nextToken()) != null) {

                // Chunk an array of JSON strings
                if (token == JsonToken.START_OBJECT) {
                    JsonNode eventNode = parser.readValueAsTree();
                    int id = eventNode.get("id").asInt();
                    String eventJsonString = objectMapper.writeValueAsString(eventNode);

                    ProducerRecord<String, String> eventRecord = buildProducerRecord(topic, timestamp, id, eventJsonString);

                    // Async send each event to Kafka cluster
                    CompletableFuture<SendResult<String, String>> sentResult = kafkaTemplate.send(eventRecord);

                    sentResult.whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send message with key={} to topic={} due to: {}", eventRecord.key(), eventRecord.topic(), ex.getMessage(), ex);
                        }
                    });

                }
            }
        } catch (Exception e) {
            log.error("{} - An error occurred while parsing the JSON events: {}", topic, e.getMessage(), e);
        }

        log.info("{} - succeed to async send events to Kafka", topic);
    }

    private ProducerRecord<String, String> buildProducerRecord(String topic, String timestamp, Integer id, String event) {
        String key = Constants.EVENT_RECORD_KEY_TEMPLATE.formatted(timestamp, id);
        return new ProducerRecord<>(topic, null, key, event);
    }
}
