package org.bugmakers404.hermes.producer.vicroad.dataentry.bluetoothRawData;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
//@Document(collection = "bluetooth_raw_data.links_with_geo")
public class LinksInfo implements Serializable {

  private Integer id;

  private String name;

  private Integer origin;

  private Integer destination;

  private Integer length;

  @JsonProperty("min_number_of_lanes")
  private Integer minNumberOfLanes;

  @JsonProperty("minimum_tt")
  private Integer minTravelTime;

  @JsonProperty("is_freeway")
  private Boolean isFreeway;

  private String direction;

  private List<List<Double>> coordinates;

  @JsonSetter("origin.id")
  public void setOriginId(Integer originId) {
    this.origin = originId;
  }

  @JsonSetter("destination.id")
  public void setDestinationId(Integer destinationId) {
    this.destination = destinationId;
  }
}
