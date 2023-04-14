package org.bugmakers404.hermes.producer.vicroad;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.http.HttpResponse;
import org.bugmakers404.hermes.producer.vicroad.task.EventsCollector;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EventsCollectorTest {

  public String key;

  public String linksUrl;

  public String linksWithGeometryUrl;

  public String routesUrl;

  public String sitesUrl;

  @BeforeClass
  public void setUp() throws IOException {
    String propertiesPath = System.getProperty("spring.config.additional-location");
    Properties properties = new Properties();
    try (FileInputStream fis = new FileInputStream(propertiesPath)) {
      properties.load(fis);
    }

    key = properties.getProperty("vicroad.subscription-key");
    linksUrl = properties.getProperty("vicroad.links.url");
    linksWithGeometryUrl = properties.getProperty("vicroad.links_with_geo.url");
    routesUrl = properties.getProperty("vicroad.routes.url");
    sitesUrl = properties.getProperty("vicroad.sites.url");
  }

  @Test
  public void testGetLinks() throws Exception {
    EventsCollector linkEventsCollector = new EventsCollector(linksUrl, key);
    HttpResponse httpResponse = linkEventsCollector.fetchData();
    assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
  }

  @Test
  public void testGetLinksWithGeo() throws Exception {
    EventsCollector linkwithGeoEventsCollector = new EventsCollector(linksWithGeometryUrl, key);
    HttpResponse httpResponse = linkwithGeoEventsCollector.fetchData();
    assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
  }

  @Test
  public void testGetRoutes() throws Exception {
    EventsCollector routeEventsCollector = new EventsCollector(routesUrl, key);
    HttpResponse httpResponse = routeEventsCollector.fetchData();
    assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
  }

  @Test
  public void testGetSites() throws Exception {
    EventsCollector siteEventsCollector = new EventsCollector(sitesUrl, key);
    HttpResponse httpResponse = siteEventsCollector.fetchData();
    assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
  }

}
