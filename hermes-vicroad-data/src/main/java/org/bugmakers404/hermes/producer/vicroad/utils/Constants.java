package org.bugmakers404.hermes.producer.vicroad.utils;

public class Constants {

  // Constants for bluetooth data collectors
  public final static int BLUETOOTH_DATA_DURATION = 30000;

  public final static int BLUETOOTH_DATA_MAX_RETRIES = 10;

  public final static int BLUETOOTH_DATA_TIMEOUT = 30000;

  public final static String VICROAD_REQUEST_KEY_NAME = "Ocp-Apim-Subscription-Key";

  // Constants for the kafka cluster
  public final static String BLUETOOTH_DATA_TOPIC_LINKS = "vicroad-links";

  public final static String BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO = "vicroad-linksWithGeometry";

  public final static String BLUETOOTH_DATA_TOPIC_ROUTES = "vicroad-routes";

  public final static String BLUETOOTH_DATA_TOPIC_SITES = "vicroad-sites";

  public final static int KAFKA_PARTITION_COUNT = 3;

  public final static int KAFKA_PARTITION_COUNT_LOCAL = 1;

  public final static int KAFKA_REPLICA_COUNT = 3;

  public final static int KAFKA_REPLICA_COUNT_LOCAL = 1;

}
