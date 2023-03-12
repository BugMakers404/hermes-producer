package org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.link;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
public class Link implements Serializable {

  private String timestamp;

  private Integer id;

  private LinkLatestStats latestStats;
}
