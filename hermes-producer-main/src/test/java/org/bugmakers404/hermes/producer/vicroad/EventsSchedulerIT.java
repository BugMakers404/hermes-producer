package org.bugmakers404.hermes.producer.vicroad;

import static org.mockito.Mockito.verify;

import org.bugmakers404.hermes.producer.vicroad.configs.EventsCollector;
import org.bugmakers404.hermes.producer.vicroad.service.EventsScheduler;
import org.bugmakers404.hermes.producer.vicroad.service.KafkaProducerServiceImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest(classes = org.bugmakers404.hermes.producer.Application.class)
public class EventsSchedulerIT extends AbstractTestNGSpringContextTests {

  @Autowired
  private EventsScheduler eventsScheduler;

  @Mock
  private EventsCollector linksCollector;

  @Mock
  private EventsCollector linksWithGeometryCollector;

  @Mock
  private EventsCollector routesCollector;

  @Mock
  private EventsCollector sitesCollector;

  @Mock
  private KafkaProducerServiceImpl kafkaProducerServiceImpl;

  @BeforeMethod
  public void setUp() {
    // TODO -- To simulate the real procedure, I need to solve the following problems:
    // 1. @Async issues.
    // 2. Handle the testing data being persistent locally.
    // 3. Handle the testing data being sent to kafka.
    MockitoAnnotations.openMocks(this);
  }

  @Test(enabled = false)
  public void testCollectLinksData() throws Exception {
    eventsScheduler.collectLinksData();
    verify(linksCollector).fetchData();
  }

  @Test(enabled = false)
  public void testCollectLinksWithGeometryData() throws Exception {
    eventsScheduler.collectLinksWithGeoData();
    verify(linksWithGeometryCollector).fetchData();
  }

  @Test(enabled = false)
  public void testCollectRoutesData() throws Exception {
    eventsScheduler.collectRoutesData();
    verify(routesCollector).fetchData();
  }

  @Test(enabled = false)
  public void testCollectSitesData() throws Exception {
    eventsScheduler.collectSitesData();
    verify(sitesCollector).fetchData();
  }
}