package org.bugmakers404.hermes.vicroad.task;

import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
public class DataCollectorConfig {
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
  public DataCollector linksCollector() throws URISyntaxException {
    return new DataCollector(linksUrl, key);
  }

  @Bean
  public DataCollector linksWithGeometryCollector() throws URISyntaxException {
    return new DataCollector(linksWithGeometryUrl, key);
  }

  @Bean
  public DataCollector routesCollector() throws URISyntaxException {
    return new DataCollector(routesUrl, key);
  }

  @Bean
  public DataCollector sitesCollector() throws URISyntaxException {
    return new DataCollector(sitesUrl, key);
  }

}
