package org.bugmakers404.hermes.producer.vicroad;

import static org.mockito.Mockito.verify;

import org.bugmakers404.hermes.producer.vicroad.task.EventsCollectionScheduler;
import org.bugmakers404.hermes.producer.vicroad.task.EventsCollector;
import org.bugmakers404.hermes.producer.vicroad.task.KafkaEventsProducer;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest(classes = org.bugmakers404.hermes.producer.Application.class)
public class EventsCollectionSchedulerIT extends AbstractTestNGSpringContextTests {

  @Autowired
  private EventsCollectionScheduler eventsCollectionScheduler;

  @Mock
  private EventsCollector linksCollector;

  @Mock
  private EventsCollector linksWithGeometryCollector;

  @Mock
  private EventsCollector routesCollector;

  @Mock
  private EventsCollector sitesCollector;

  @Mock
  private KafkaEventsProducer kafkaEventsProducer;

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
    eventsCollectionScheduler.collectLinksData();
    verify(linksCollector).fetchData();
  }

  @Test(enabled = false)
  public void testCollectLinksWithGeometryData() throws Exception {
    eventsCollectionScheduler.collectLinksWithGeometryData();
    verify(linksWithGeometryCollector).fetchData();
  }

  @Test(enabled = false)
  public void testCollectRoutesData() throws Exception {
    eventsCollectionScheduler.collectRoutesData();
    verify(routesCollector).fetchData();
  }

  @Test(enabled = false)
  public void testCollectSitesData() throws Exception {
    eventsCollectionScheduler.collectSitesData();
    verify(sitesCollector).fetchData();
  }
}