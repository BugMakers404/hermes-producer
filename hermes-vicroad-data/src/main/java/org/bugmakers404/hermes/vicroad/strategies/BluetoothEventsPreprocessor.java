package org.bugmakers404.hermes.vicroad.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.link.Link;
import org.springframework.stereotype.Component;

@Component
public class BluetoothEventsPreprocessor {

  private ObjectMapper objectMapper = new ObjectMapper();

  public List<Link> filterLinksForKafka(String rawEvents) throws JsonProcessingException {

    List<Link> refinedLinks = objectMapper.readValue(rawEvents, new TypeReference<>() {
    });

    Optional<Link> firstLinkWithStats = refinedLinks.stream().filter(link -> link.getLatestStats() != null).findFirst();

    if (firstLinkWithStats.isPresent()) {
      DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
      LocalDateTime timeStamp = LocalDateTime.parse(firstLinkWithStats.get().getLatestStats().getIntervalStartTime(),
          formatter);
      refinedLinks.forEach(link -> link.setTimestamp(timeStamp));

      return refinedLinks;
    } else {
      return List.of();
    }

  }
}
