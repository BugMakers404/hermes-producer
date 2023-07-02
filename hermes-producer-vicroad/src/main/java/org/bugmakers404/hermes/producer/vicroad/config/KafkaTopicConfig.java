package org.bugmakers404.hermes.producer.vicroad.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.bugmakers404.hermes.producer.vicroad.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

  @Bean
  public NewTopic topicOfLinks() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_LINKS)
        .partitions(Constants.KAFKA_PARTITION_COUNT)
        .replicas(Constants.KAFKA_REPLICA_COUNT).build();
  }

  @Bean
  public NewTopic topicOfLinksWithGeo() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO)
        .partitions(Constants.KAFKA_PARTITION_COUNT).replicas(Constants.KAFKA_REPLICA_COUNT)
        .build();
  }

  @Bean
  public NewTopic topicOfRoutes() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_ROUTES)
        .partitions(Constants.KAFKA_PARTITION_COUNT)
        .replicas(Constants.KAFKA_REPLICA_COUNT).build();
  }

  @Bean
  public NewTopic topicOfRoutesWithGeo() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_ROUTES_WITH_GEO)
        .partitions(Constants.KAFKA_PARTITION_COUNT).replicas(Constants.KAFKA_REPLICA_COUNT)
        .build();
  }

  @Bean
  public NewTopic topicOfSites() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_SITES)
        .partitions(Constants.KAFKA_PARTITION_COUNT)
        .replicas(Constants.KAFKA_REPLICA_COUNT).build();
  }

  @Bean
  public NewTopic topicOfSitesWithGeo() {
    return TopicBuilder.name(Constants.BLUETOOTH_DATA_TOPIC_SITES_WITH_GEO)
        .partitions(Constants.KAFKA_PARTITION_COUNT).replicas(Constants.KAFKA_REPLICA_COUNT)
        .build();
  }

}
