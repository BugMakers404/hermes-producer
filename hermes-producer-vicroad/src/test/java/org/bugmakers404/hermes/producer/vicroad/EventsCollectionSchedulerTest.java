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
import org.bugmakers404.hermes.producer.vicroad.task.AggregatedEventsCollector;
import org.bugmakers404.hermes.producer.vicroad.task.EventsCollectionScheduler;
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
  private AggregatedEventsCollector aggregatedEventsCollector;

  @Mock
  private KafkaEventsProducer kafkaEventsProducer;

  private EventsCollectionScheduler eventsCollectionScheduler;

  private final String successResponseContent = "[{'latest_stats': {'interval_start': '2023-03-20T18:08:00+11:00'}}]";

  private final String failedResponseContent = "[]";

  private AutoCloseable closeable;

  @BeforeMethod
  public void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    eventsCollectionScheduler = new EventsCollectionScheduler(aggregatedEventsCollector, kafkaEventsProducer);
  }

  @AfterMethod
  public void tearDown() throws Exception {
    // eventsCollectionScheduler will collect data and archive them, so it is required to delete the mock data.
    Path archiveDirectoryPath = Paths.get(VICROAD_DATA_ARCHIVES_ROOT);
    if (Files.exists(archiveDirectoryPath)) {
      boolean isDeleted = FileSystemUtils.deleteRecursively(archiveDirectoryPath);
      assertTrue(isDeleted);
    }

    closeable.close();
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
    BasicHttpResponse successResponse = createMockedHttpResponse(200, successResponseContent);

    when(aggregatedEventsCollector.fetchLinksData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectLinksData();
    verify(aggregatedEventsCollector, times(1)).fetchLinksData();
  }

  @Test
  public void collectLinksDataFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(aggregatedEventsCollector.fetchLinksData()).thenReturn(failResponse);
    eventsCollectionScheduler.collectLinksData();
    verify(aggregatedEventsCollector, times(Constants.BLUETOOTH_DATA_MAX_RETRIES)).fetchLinksData();
  }

  @Test
  public void collectLinksWithGeoDataSuccessTest() throws IOException {
    BasicHttpResponse successResponse = createMockedHttpResponse(200, successResponseContent);

    when(aggregatedEventsCollector.fetchLinksWithGeoData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectLinksWithGeoData();
    verify(aggregatedEventsCollector, times(1)).fetchLinksWithGeoData();
  }

  @Test
  public void collectLinksWithGeoDataFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(aggregatedEventsCollector.fetchLinksWithGeoData()).thenReturn(failResponse);
    eventsCollectionScheduler.collectLinksWithGeoData();
    verify(aggregatedEventsCollector, times(Constants.BLUETOOTH_DATA_MAX_RETRIES)).fetchLinksWithGeoData();
  }

  @Test
  public void collectRoutesDataSuccessTest() throws IOException {
    BasicHttpResponse successResponse = createMockedHttpResponse(200, successResponseContent);

    when(aggregatedEventsCollector.fetchRoutesData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectRoutesData();
    verify(aggregatedEventsCollector, times(1)).fetchRoutesData();
  }

  @Test
  public void collectRoutesDataFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(aggregatedEventsCollector.fetchRoutesData()).thenReturn(failResponse);
    eventsCollectionScheduler.collectRoutesData();
    verify(aggregatedEventsCollector, times(Constants.BLUETOOTH_DATA_MAX_RETRIES)).fetchRoutesData();
  }

  @Test
  public void collectSitesDataSuccessTest() throws IOException {
    BasicHttpResponse successResponse = createMockedHttpResponse(200, successResponseContent);

    when(aggregatedEventsCollector.fetchSitesData()).thenReturn(successResponse);
    eventsCollectionScheduler.collectSitesData();
    verify(aggregatedEventsCollector, times(1)).fetchSitesData();
  }

  @Test
  public void collectSitesDataFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(aggregatedEventsCollector.fetchSitesData()).thenReturn(failResponse);
    eventsCollectionScheduler.collectSitesData();
    verify(aggregatedEventsCollector, times(Constants.BLUETOOTH_DATA_MAX_RETRIES)).fetchSitesData();
  }

}

