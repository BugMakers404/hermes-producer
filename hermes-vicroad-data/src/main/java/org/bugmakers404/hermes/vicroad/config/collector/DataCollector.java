package org.bugmakers404.hermes.vicroad.config.collector;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.NonNull;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bugmakers404.hermes.vicroad.utils.Constants;

public class DataCollector {

  @NonNull
  public String url;

  @NonNull
  public String key;

  public CloseableHttpClient httpClient;

  public HttpGet clientGetRequest;

  public DataCollector(String url, String key) throws URISyntaxException {
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
