package org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Data
@AllArgsConstructor
@Document(collection = "bluetooth_raw_data.routes")
public class Routes implements Serializable {

  @Id
  public String timestamp;

  public String routesRawData;
}
