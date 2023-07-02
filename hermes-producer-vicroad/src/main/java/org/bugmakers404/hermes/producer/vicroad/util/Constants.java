package org.bugmakers404.hermes.producer.vicroad.util;

import java.time.format.DateTimeFormatter;

public class Constants {

  // Constants for bluetooth data collectors
  public final static int BLUETOOTH_DEFAULT_COLLECTION_INTERVAL = 30000;

  public final static int BLUETOOTH_SITE_COLLECTION_INTERVAL = 300000;

  public final static String BLUETOOTH_LINK_WITH_GEO_DATA_COLLECTION_TIME = "0 0 3 * * ?";
  public final static String BLUETOOTH_ROUTE_WITH_GEO_DATA_COLLECTION_TIME = "0 5 3 * * ?";

  public final static String BLUETOOTH_SITE_WITH_GEO_DATA_COLLECTION_TIME = "0 10 3 * * ?";

  public final static int BLUETOOTH_MAX_COLLECTION_RETRIES = 10;

  public final static int BLUETOOTH_DATA_TIMEOUT = 30000;

  public final static String VICROAD_REQUEST_KEY_NAME = "Ocp-Apim-Subscription-Key";

  // Constants for the kafka cluster
  public final static String BLUETOOTH_DATA_TOPIC_LINKS = "vicroad-links";

  public final static String BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO = "vicroad-linksWithGeo";

  public final static String BLUETOOTH_DATA_TOPIC_ROUTES = "vicroad-routes";

  public final static String BLUETOOTH_DATA_TOPIC_ROUTES_WITH_GEO = "vicroad-routesWithGeo";

  public final static String BLUETOOTH_DATA_TOPIC_SITES = "vicroad-sites";

  public final static String BLUETOOTH_DATA_TOPIC_SITES_WITH_GEO = "vicroad-sitesWithGeo";

  public final static int KAFKA_PARTITION_COUNT = 1;

  public final static int KAFKA_REPLICA_COUNT = 1;

  public final static String EVENT_RECORD_KEY_TEMPLATE = "%s_%d";

  // Constants for Timestamp format
  public final static DateTimeFormatter DATE_TIME_FORMATTER_FOR_FILENAME = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd_HH-mm-ssx");

  public final static DateTimeFormatter DATE_TIME_FORMATTER_FOR_KAFKA = DateTimeFormatter.ofPattern(
          "yyyy-MM-dd'T'HH:mm:ssXXX");

  // Constants for AWS S3
  public final static String HERMES_DATA_BUCKET_NAME = "hermes-data-archives";

  // Constants for archiving data
  public final static String VICROAD_DATA_ARCHIVES_ROOT = "vicroad_data_archives";

  public final static String VICROAD_DATA_ARCHIVE_COMMON_DIR =
      VICROAD_DATA_ARCHIVES_ROOT + "/common";

  public final static String BLUETOOTH_DATA_ARCHIVES_DIR =
      VICROAD_DATA_ARCHIVE_COMMON_DIR + "/bluetooth";

  public final static String BLUETOOTH_DATA_ARCHIVES_TOPIC_DIR =
      BLUETOOTH_DATA_ARCHIVES_DIR + "/%s";

  public final static String BLUETOOTH_DATA_ARCHIVES_EVENT_PATH =
      BLUETOOTH_DATA_ARCHIVES_TOPIC_DIR + "/%s.json";

}
