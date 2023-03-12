package org.bugmakers404.hermes.vicroad.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.link.Link;
import org.springframework.stereotype.Component;

@Component
public class BluetoothEventsPreprocessor {

  private ObjectMapper objectMapper = new ObjectMapper();

  public String filterLinksForKafka(String rawEvents) throws JsonProcessingException {
    List<Link> filteredLinks = objectMapper.readValue(rawEvents, new TypeReference<>() {
    });

    filteredLinks.forEach(
        link -> link.setTimestamp(link.getLatestStats() == null ? null : link.getLatestStats().getIntervalStartTime()));

    return filteredLinks;
  }
}
