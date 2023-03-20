package org.bugmakers404.hermes.producer.vicroad.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

  @Bean
  public NewTopic topicOfLinks() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_LINKS).partitions(Constants.KAFKA_PARTITION_COUNT_LOCAL)
        .replicas(Constants.KAFKA_REPLICA_COUNT_LOCAL).build();
  }

  @Bean
  public NewTopic topicOfLinksWithGeometry() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO)
        .partitions(Constants.KAFKA_PARTITION_COUNT_LOCAL).replicas(Constants.KAFKA_REPLICA_COUNT_LOCAL).build();
  }

  @Bean
  public NewTopic topicOfRoutes() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_ROUTES).partitions(Constants.KAFKA_PARTITION_COUNT_LOCAL)
        .replicas(Constants.KAFKA_REPLICA_COUNT_LOCAL).build();
  }

  @Bean
  public NewTopic topicOfSites() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_SITES).partitions(Constants.KAFKA_PARTITION_COUNT_LOCAL)
        .replicas(Constants.KAFKA_REPLICA_COUNT_LOCAL).build();
  }

}
