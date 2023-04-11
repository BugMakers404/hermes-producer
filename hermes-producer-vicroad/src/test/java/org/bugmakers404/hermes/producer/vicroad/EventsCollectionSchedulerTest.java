package org.bugmakers404.hermes.producer.vicroad;

import static org.bugmakers404.hermes.producer.vicroad.utils.Constants.VICROAD_DATA_ARCHIVES_ROOT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.bugmakers404.hermes.producer.vicroad.task.EventsCollectionScheduler;
import org.bugmakers404.hermes.producer.vicroad.task.EventsCollector;
import org.bugmakers404.hermes.producer.vicroad.task.KafkaEventsProducer;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.FileSystemUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EventsCollectionSchedulerTest {

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

  private EventsCollectionScheduler eventsCollectionScheduler;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    eventsCollectionScheduler = new EventsCollectionScheduler(linksCollector, linksWithGeometryCollector,
        routesCollector, sitesCollector, kafkaEventsProducer);
  }

  @AfterMethod
  public void tearDown() throws IOException {
    // eventsCollectionScheduler will collect data and archive them so it is required to delete the mock data.
    Path archiveDirectoryPath = Paths.get(VICROAD_DATA_ARCHIVES_ROOT);
    if (Files.exists(archiveDirectoryPath)) {
      boolean isDeleted = FileSystemUtils.deleteRecursively(archiveDirectoryPath);
      assertTrue(isDeleted);
    }
  }

  private BasicHttpResponse createMockedHttpResponse(int statusCode, String content) {
    BasicHttpResponse response = mock(BasicHttpResponse.class);
    BasicStatusLine statusLine = mock(BasicStatusLine.class);

    when(response.getStatusLine()).thenReturn(statusLine);
    when(statusLine.getStatusCode()).thenReturn(statusCode);
    when(response.getEntity()).thenReturn(new StringEntity(content, "UTF-8"));
    return response;
  }

  @Test
  public void collectLinksDataSuccessTest() throws IOException {
    String content = "Successfully collected links data.";
    BasicHttpResponse successResponse = createMockedHttpResponse(200, content);

    when(linksCollector.fetchData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectLinksData();
    verify(linksCollector, times(1)).fetchData();
  }

  @Test
  public void collectLinksDataFailureTest() throws IOException {
    String content = "Failed to collected links data.";
    BasicHttpResponse successResponse = createMockedHttpResponse(500, content);

    when(linksCollector.fetchData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectLinksData();
    verify(linksCollector, times(Constants.BLUETOOTH_DATA_MAX_RETRIES)).fetchData();
  }

  @Test
  public void collectLinksWithGeoDataSuccessTest() throws IOException {
    String content = "Successfully collected links with Geo data.";
    BasicHttpResponse successResponse = createMockedHttpResponse(200, content);

    when(linksWithGeometryCollector.fetchData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectLinksWithGeometryData();
    verify(linksWithGeometryCollector, times(1)).fetchData();
  }

  @Test
  public void collectLinksWithGeoDataFailureTest() throws IOException {
    String content = "Failed to collected links with Geo data.";
    BasicHttpResponse successResponse = createMockedHttpResponse(500, content);

    when(linksWithGeometryCollector.fetchData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectLinksWithGeometryData();
    verify(linksWithGeometryCollector, times(Constants.BLUETOOTH_DATA_MAX_RETRIES)).fetchData();
  }

  @Test
  public void collectRoutesDataSuccessTest() throws IOException {
    String content = "Successfully collected routes data.";
    BasicHttpResponse successResponse = createMockedHttpResponse(200, content);

    when(routesCollector.fetchData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectRoutesData();
    verify(routesCollector, times(1)).fetchData();
  }

  @Test
  public void collectRoutesDataFailureTest() throws IOException {
    String content = "Failed to collected routes data.";
    BasicHttpResponse successResponse = createMockedHttpResponse(500, content);

    when(routesCollector.fetchData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectRoutesData();
    verify(routesCollector, times(Constants.BLUETOOTH_DATA_MAX_RETRIES)).fetchData();
  }

  @Test
  public void collectSitesDataSuccessTest() throws IOException {
    String content = "Successfully collected sites data.";
    BasicHttpResponse successResponse = createMockedHttpResponse(200, content);

    when(sitesCollector.fetchData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectSitesData();
    verify(sitesCollector, times(1)).fetchData();
  }

  @Test
  public void collectSitesDataFailureTest() throws IOException {
    String content = "Failed to collected sites data.";
    BasicHttpResponse successResponse = createMockedHttpResponse(500, content);

    when(sitesCollector.fetchData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectSitesData();
    verify(sitesCollector, times(Constants.BLUETOOTH_DATA_MAX_RETRIES)).fetchData();
  }

}

