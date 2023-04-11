package org.bugmakers404.hermes.producer.vicroad.configs;

import java.net.URISyntaxException;
import org.bugmakers404.hermes.producer.vicroad.task.EventsCollector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsCollectorConfig {
  @Value("${vicroad.subscription-key}")
  public String key;

  @Value("${vicroad.links.url}")
  public String linksUrl;

  @Value("${vicroad.links_with_geo.url}")
  public String linksWithGeometryUrl;

  @Value("${vicroad.routes.url}")
  public String routesUrl;

  @Value("${vicroad.sites.url}")
  public String sitesUrl;


  @Bean
  public EventsCollector linksCollector() throws URISyntaxException {
    return new EventsCollector(linksUrl, key);
  }

  @Bean
  public EventsCollector linksWithGeometryCollector() throws URISyntaxException {
    return new EventsCollector(linksWithGeometryUrl, key);
  }

  @Bean
  public EventsCollector routesCollector() throws URISyntaxException {
    return new EventsCollector(routesUrl, key);
  }

  @Bean
  public EventsCollector sitesCollector() throws URISyntaxException {
    return new EventsCollector(sitesUrl, key);
  }

}
