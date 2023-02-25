package org.bugmakers404.hermes.vicroad.task;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BluetoothTravelTimeCollector {

  @Value("${vicroad.travel-time.url}")
  public String bluetoothTravelTimeUrl;

  @Value("${vicroad.travel-time.subscription-key}")
  public String bluetoothTravelTimeKey;

  public CloseableHttpClient httpClient;

  public HttpGet clientRequest;

  @PostConstruct
  void initHttpClient() throws URISyntaxException {
    this.httpClient = HttpClients.createDefault();
    URIBuilder builder = new URIBuilder(bluetoothTravelTimeUrl);
    URI uri = builder.build();
    clientRequest = new HttpGet(uri);
    clientRequest.setHeader("Ocp-Apim-Subscription-Key", bluetoothTravelTimeKey);
  }

  public HttpResponse fetchData() throws IOException {
    return this.httpClient.execute(clientRequest);
  }
}
