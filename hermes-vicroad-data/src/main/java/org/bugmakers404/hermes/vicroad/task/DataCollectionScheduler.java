package org.bugmakers404.hermes.vicroad.task;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableAsync
@Configuration
@RequiredArgsConstructor
public class DataCollectionScheduler {
  private final BluetoothTravelTimeCollector bluetoothTravelTimeCollector;

  @Scheduled(fixedRate = 30000)
  public void collectBluetoothTravelTime() {

    try {
      HttpEntity entity = bluetoothTravelTimeCollector.fetchData().getEntity();

      if (entity != null) {
//        System.out.println(EntityUtils.toString(entity));
        System.out.println("yes");
      }

    } catch (Exception e) {
      System.out.println(e.getMessage().length());
    }

  }
}
