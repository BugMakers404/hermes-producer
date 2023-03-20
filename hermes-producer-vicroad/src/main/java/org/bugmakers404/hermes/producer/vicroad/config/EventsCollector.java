package org.bugmakers404.hermes.producer.vicroad.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.Data;
import lombok.NonNull;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bugmakers404.hermes.producer.vicroad.utils.Constants;

@Data
public class EventsCollector {

  @NonNull
  private String url;

  @NonNull
  private String key;

  private CloseableHttpClient httpClient;

  private HttpGet clientGetRequest;




  public EventsCollector(String url, String key) throws URISyntaxException {
    this.url = url;
    this.key = key;
    this.httpClient = HttpClients.createDefault();
    URIBuilder builder = new URIBuilder(url);
    URI uri = builder.build();
    clientGetRequest = new HttpGet(uri);
    clientGetRequest.setHeader(Constants.VICROAD_REQUEST_KEY_NAME, key);
  }

  public HttpResponse fetchData() throws IOException {
    return this.httpClient.execute(clientGetRequest);
  }
}
