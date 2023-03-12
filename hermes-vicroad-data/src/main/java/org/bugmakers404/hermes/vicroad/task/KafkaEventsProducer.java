package org.bugmakers404.hermes.vicroad.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.link.Link;
import org.bugmakers404.hermes.vicroad.utils.Constants;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaEventsProducer {

  public final KafkaTemplate<String, String> kafkaTemplate;

  private ObjectMapper objectMapper = new ObjectMapper();

  public void sendLinksEvent(List<Link> links) throws JsonProcessingException {
    for (Link link : links) {
      CompletableFuture<SendResult<String, String>> sentLinksResult = kafkaTemplate.send(
          Constants.BLUETOOTH_DATA_TOPIC_LINKS, LocalDateTime.now().toString(), objectMapper.writeValueAsString(link));

      sentLinksResult.whenCompleteAsync(this::handleSendResult);
    }
  }

  private void handleSendResult(SendResult<String, String> result, Throwable exception) {
    if (exception == null) {
      handleSuccess(result);
    } else {
      handleFailure(exception);
    }
  }

  private void handleSuccess(SendResult<String, String> result) {
    RecordMetadata recordMetadata = result.getRecordMetadata();
    log.info("Message Sent Successfully for the topic {} at partition {}.", recordMetadata.topic(), recordMetadata.partition());
  }

  public void handleFailure(Throwable exception) {
    ProducerRecord<String, String> failedRecord = ((KafkaProducerException) exception).getFailedProducerRecord();
    log.error("An error occurs when sending message for the topic {} at partition{}", failedRecord.topic(),
        failedRecord.partition());
    log.error(exception.getMessage());
  }
}
