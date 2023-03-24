package org.bugmakers404.hermes.producer.vicroad.task;

import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.BLUETOOTH_DATA_TOPIC_LINKS;
import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO;
import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.BLUETOOTH_DATA_TOPIC_ROUTES;
import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.BLUETOOTH_DATA_TOPIC_SITES;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaEventsProducer {

  public final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

  public void sendLinkEvents(String linkEvents) throws JsonProcessingException {
    sendProducerRecords(BLUETOOTH_DATA_TOPIC_LINKS, linkEvents);
  }

  public void sendLinkWithGeoEvents(String linkWithGeoEvents) throws JsonProcessingException {
    sendProducerRecords(BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO, linkWithGeoEvents);
  }

  public void sendRouteEvents(String routeEvents) throws JsonProcessingException {
    sendProducerRecords(BLUETOOTH_DATA_TOPIC_ROUTES, routeEvents);
  }

  public void sendSiteEvents(String siteEvents) throws JsonProcessingException {
    sendProducerRecords(BLUETOOTH_DATA_TOPIC_SITES, siteEvents);
  }

  private void sendProducerRecords(String topic, String events) throws JsonProcessingException {
    List<Map<String, Object>> eventsList = objectMapper.readValue(events, new TypeReference<>() {
    });

    for (Map<String, Object> event : eventsList) {
      ProducerRecord<String, String> eventRecord = buildProducerRecord(topic, event);
      CompletableFuture<SendResult<String, String>> sentLinksResult = kafkaTemplate.send(eventRecord);
      sentLinksResult.whenCompleteAsync(this::handleSendResult);
    }
  }

  private ProducerRecord<String, String> buildProducerRecord(String topic, Map<String, Object> event)
      throws JsonProcessingException {
    String key = Constants.EVENT_RECORD_KEY_TEMPLATE.formatted(LocalDateTime.now(), (Integer) event.get("id"));
    return new ProducerRecord<>(topic, null, key, objectMapper.writeValueAsString(event));
  }

  private void handleSendResult(SendResult<String, String> result, Throwable exception) {
    if (exception != null) {
      handleFailure(exception);
    }
  }

  //  private void handleSuccess(SendResult<String, String> result) {
  //    RecordMetadata recordMetadata = result.getRecordMetadata();
  //    log.info("Message Sent Successfully for the topic {} at partition {}.", recordMetadata.topic(),
  //        recordMetadata.partition());
  //  }

  public void handleFailure(Throwable exception) {
    ProducerRecord<String, String> failedRecord = ((KafkaProducerException) exception).getFailedProducerRecord();
    log.error("An error occurs when sending message for the topic {} at partition{}", failedRecord.topic(),
        failedRecord.partition());
    log.error(exception.getMessage());
  }
}
