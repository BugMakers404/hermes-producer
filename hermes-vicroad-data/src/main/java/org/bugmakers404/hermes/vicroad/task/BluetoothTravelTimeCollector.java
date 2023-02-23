package org.bugmakers404.hermes.vicroad.task;

import java.net.URI;
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

@EnableScheduling
@EnableAsync
@Configuration
public class BluetoothTravelTimeCollector {

  @Value("${vicroad.travel-time.url}")
  public String bluetoothTravelTimeUrl;

  @Value("${vicroad.travel-time.subscription-key}")
  public String bluetoothTravelTimeKey;

  @Scheduled(fixedRate = 30000)
  public void collectBluetoothTravelTime() {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      URIBuilder builder = new URIBuilder(bluetoothTravelTimeUrl);
      URI uri = builder.build();
      HttpGet request = new HttpGet(uri);
      request.setHeader("Ocp-Apim-Subscription-Key", bluetoothTravelTimeKey);

      HttpResponse response = httpclient.execute(request);
      HttpEntity entity = response.getEntity();

      if (entity != null) {
        System.out.println(EntityUtils.toString(entity));
      }

    } catch (Exception e) {
      System.out.println(e.getMessage().length());
    }

  }
}
