package org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData;

import java.io.Serializable;
import lombok.Data;

@Data
public class BluetoothSitesRawData implements Serializable {
  public String timestamp;

  public String sitesRawData;
}
