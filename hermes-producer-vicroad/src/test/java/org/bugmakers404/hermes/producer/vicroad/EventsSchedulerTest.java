package org.bugmakers404.hermes.producer.vicroad;

import static org.bugmakers404.hermes.producer.vicroad.util.Constants.VICROAD_DATA_ARCHIVES_ROOT;
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
import org.bugmakers404.hermes.producer.vicroad.service.EventsSchedulerServiceImpl;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsArchiveService;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsCollectionService;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsSchedulerService;
import org.bugmakers404.hermes.producer.vicroad.service.interfaces.EventsSendingService;
import org.bugmakers404.hermes.producer.vicroad.util.Constants;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.FileSystemUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EventsSchedulerTest {

  @Mock
  private EventsCollectionService eventsCollectionService;

  @Mock
  private EventsSendingService eventsSendingService;

  @Mock
  private EventsArchiveService eventsArchiveService;

  private EventsSchedulerService eventsScheduler;

  private final String successResponseContent = "[{\"latest_stats\": {\"interval_start\": \"2023-03-20T18:08:00+11:00\"}}]";

  private final String failedResponseContent = "[]";

  private AutoCloseable closeable;

  @BeforeMethod
  public void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    eventsScheduler = new EventsSchedulerServiceImpl(eventsCollectionService, eventsSendingService,
        eventsArchiveService);
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

    when(eventsCollectionService.fetchData(Constants.BLUETOOTH_DATA_TOPIC_LINKS)).thenReturn(
        successResponse);
    eventsScheduler.collectAndSendLinksData();
    verify(eventsCollectionService, times(1)).fetchData(Constants.BLUETOOTH_DATA_TOPIC_LINKS);
  }

  @Test
  public void collectLinksDataFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(eventsCollectionService.fetchData(Constants.BLUETOOTH_DATA_TOPIC_LINKS)).thenReturn(
        failResponse);
    eventsScheduler.collectAndSendLinksData();
    verify(eventsCollectionService, times(Constants.BLUETOOTH_MAX_COLLECTION_RETRIES)).fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_LINKS);
  }

  @Test
  public void collectLinksWithGeoDataSuccessTest() throws IOException {
    BasicHttpResponse successResponse = createMockedHttpResponse(200, successResponseContent);

    when(eventsCollectionService.fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO)).thenReturn(successResponse);
    eventsScheduler.collectAndSendLinksWithGeoData();
    verify(eventsCollectionService, times(1)).fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO);
  }

  @Test
  public void collectLinksWithGeoDataFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(eventsCollectionService.fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO)).thenReturn(failResponse);
    eventsScheduler.collectAndSendLinksWithGeoData();
    verify(eventsCollectionService,
        times(Constants.BLUETOOTH_MAX_COLLECTION_RETRIES)).fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_LINKS_WITH_GEO);
  }

  @Test
  public void collectRoutesDataSuccessTest() throws IOException {
    BasicHttpResponse successResponse = createMockedHttpResponse(200, successResponseContent);

    when(eventsCollectionService.fetchData(Constants.BLUETOOTH_DATA_TOPIC_ROUTES)).thenReturn(
        successResponse);
    eventsScheduler.collectAndSendRoutesData();
    verify(eventsCollectionService, times(1)).fetchData(Constants.BLUETOOTH_DATA_TOPIC_ROUTES);
  }

  @Test
  public void collectRoutesDataFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(eventsCollectionService.fetchData(Constants.BLUETOOTH_DATA_TOPIC_ROUTES)).thenReturn(
        failResponse);
    eventsScheduler.collectAndSendRoutesData();
    verify(eventsCollectionService,
        times(Constants.BLUETOOTH_MAX_COLLECTION_RETRIES)).fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_ROUTES);
  }

  @Test
  public void collectRoutesWithGeoDataSuccessTest() throws IOException {
    BasicHttpResponse successResponse = createMockedHttpResponse(200, successResponseContent);

    when(eventsCollectionService.fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_ROUTES_WITH_GEO)).thenReturn(
        successResponse);
    eventsScheduler.collectAndSendRoutesWithGeoData();
    verify(eventsCollectionService, times(1)).fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_ROUTES_WITH_GEO);
  }

  @Test
  public void collectRoutesWithGeoDataFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(eventsCollectionService.fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_ROUTES_WITH_GEO)).thenReturn(
        failResponse);
    eventsScheduler.collectAndSendRoutesWithGeoData();
    verify(eventsCollectionService,
        times(Constants.BLUETOOTH_MAX_COLLECTION_RETRIES)).fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_ROUTES_WITH_GEO);
  }

  @Test
  public void collectSitesDataSuccessTest() throws IOException {
    BasicHttpResponse successResponse = createMockedHttpResponse(200, successResponseContent);

    when(eventsCollectionService.fetchData(Constants.BLUETOOTH_DATA_TOPIC_SITES)).thenReturn(
        successResponse);
    eventsScheduler.collectAndSendSitesData();
    verify(eventsCollectionService, times(1)).fetchData(Constants.BLUETOOTH_DATA_TOPIC_SITES);
  }

  @Test
  public void collectSitesDataFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(eventsCollectionService.fetchData(Constants.BLUETOOTH_DATA_TOPIC_SITES)).thenReturn(
        failResponse);
    eventsScheduler.collectAndSendSitesData();
    verify(eventsCollectionService,
        times(Constants.BLUETOOTH_MAX_COLLECTION_RETRIES)).fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_SITES);
  }

  @Test
  public void collectSitesWithGeoDataSuccessTest() throws IOException {
    BasicHttpResponse successResponse = createMockedHttpResponse(200, successResponseContent);

    when(eventsCollectionService.fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_SITES_WITH_GEO)).thenReturn(
        successResponse);
    eventsScheduler.collectAndSendSitesWithGeoData();
    verify(eventsCollectionService, times(1)).fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_SITES_WITH_GEO);
  }

  @Test
  public void collectSitesDataWithGeoFailureTest() throws IOException {
    BasicHttpResponse failResponse = createMockedHttpResponse(500, failedResponseContent);

    when(eventsCollectionService.fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_SITES_WITH_GEO)).thenReturn(
        failResponse);
    eventsScheduler.collectAndSendSitesWithGeoData();
    verify(eventsCollectionService,
        times(Constants.BLUETOOTH_MAX_COLLECTION_RETRIES)).fetchData(
        Constants.BLUETOOTH_DATA_TOPIC_SITES_WITH_GEO);
  }

}

