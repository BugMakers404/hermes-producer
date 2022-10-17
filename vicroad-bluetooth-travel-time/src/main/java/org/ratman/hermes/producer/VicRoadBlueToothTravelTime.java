package org.ratman.hermes.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class VicRoadBlueToothTravelTime {
  public static void main(String[] args) {
    SpringApplication.run(VicRoadBlueToothTravelTime.class, args);
  }
}
