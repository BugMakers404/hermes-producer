package org.bugmakers404.hermes.producer.vicroad.utils;

import java.time.format.DateTimeFormatter;

public class Constants {

    // Constants for bluetooth data collectors
    public final static int BASIC_BLUETOOTH_DATA_DURATION = 30000;

    public final static int BLUETOOTH_DATA_SITE_DURATION = 300000;

    public final static int BLUETOOTH_DATA_MAX_RETRIES = 10;

    public final static int BLUETOOTH_DATA_TIMEOUT = 30000;

    public final static String VICROAD_REQUEST_KEY_NAME = "Ocp-Apim-Subscription-Key";

    // Constants for the kafka cluster
    public final static String BLUETOOTH_DATA_TOPIC_LINKS = "vicroad-links";

    public final static String BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO = "vicroad-linksWithGeometry";

    public final static String BLUETOOTH_DATA_TOPIC_ROUTES = "vicroad-routes";

    public final static String BLUETOOTH_DATA_TOPIC_SITES = "vicroad-sites";

    //  public final static int KAFKA_PARTITION_COUNT = 3;

    public final static int KAFKA_PARTITION_COUNT_LOCAL = 1;

    //  public final static int KAFKA_REPLICA_COUNT = 3;

    public final static int KAFKA_REPLICA_COUNT_LOCAL = 1;

    public final static DateTimeFormatter DATE_TIME_FORMATTER_FOR_FILENAME = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd_HH-mm-ss");

    public final static DateTimeFormatter DATE_TIME_FORMATTER_FOR_KAFKA = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd'T'HH:mm:ssXXX");

    public final static String EVENT_RECORD_KEY_TEMPLATE = "%s_%d";

    public final static String VICROAD_DATA_ARCHIVES_ROOT = "../vicroad_data_archives";

    public final static String BLUETOOTH_DATA_ARCHIVES_DIR = VICROAD_DATA_ARCHIVES_ROOT + "/bluetooth";

    public final static String LINKS_ARCHIVES_DIR = BLUETOOTH_DATA_ARCHIVES_DIR + "/links";

    public final static String LINKS_FILE_PATH = LINKS_ARCHIVES_DIR + "/%s.json";

    public final static String LINKS_WITH_GEO_ARCHIVES_DIR = BLUETOOTH_DATA_ARCHIVES_DIR + "/links_with_geo";

    public final static String LINKS_WITH_GEO_FILE_PATH = LINKS_WITH_GEO_ARCHIVES_DIR + "/%s.json";

    public final static String ROUTES_ARCHIVES_DIR = BLUETOOTH_DATA_ARCHIVES_DIR + "/routes";

    public final static String ROUTES_FILE_PATH = ROUTES_ARCHIVES_DIR + "/%s.json";

    public final static String SITES_ARCHIVES_DIR = BLUETOOTH_DATA_ARCHIVES_DIR + "/sites";

    public final static String SITES_FILE_PATH = SITES_ARCHIVES_DIR + "/%s.json";

}
