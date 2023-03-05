package org.bugmakers404.hermes.vicroad.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.bugmakers404.hermes.vicroad.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AutoCreateConfig {

  @Bean
  public NewTopic topicOfLinks() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_LINKS).partitions(Constants.KAFKA_PARTITION_COUNT)
        .replicas(Constants.KAFKA_REPLICA_COUNT).build();
  }

  @Bean
  public NewTopic topicOfLinksWithGeometry() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO).partitions(Constants.KAFKA_PARTITION_COUNT)
        .replicas(Constants.KAFKA_REPLICA_COUNT).build();
  }

  @Bean
  public NewTopic topicOfRoutes() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_ROUTES).partitions(Constants.KAFKA_PARTITION_COUNT)
        .replicas(Constants.KAFKA_REPLICA_COUNT).build();
  }

  @Bean
  public NewTopic topicOfSites() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_SITES).partitions(Constants.KAFKA_PARTITION_COUNT)
        .replicas(Constants.KAFKA_REPLICA_COUNT).build();
  }

}
