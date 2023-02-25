package org.bugmakers404.hermes.vicroad.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableAsync
@Configuration
@RequiredArgsConstructor
@Slf4j
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
