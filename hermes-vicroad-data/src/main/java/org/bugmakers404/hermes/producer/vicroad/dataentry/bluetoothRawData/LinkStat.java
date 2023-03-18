package org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
//@Document(collection = "bluetooth_raw_data.links")
public class LinkStat implements Serializable {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime timestamp;

  private Integer id;

  private Boolean enabled;

  private Boolean draft;

  @JsonProperty("latest_stats")
  private LatestStats latestStats;
}
