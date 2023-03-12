package org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
//@Document(collection = "bluetooth_raw_data.sites")
public class Sites implements Serializable {

  public String timestamp;

  public String sitesRawData;
}
