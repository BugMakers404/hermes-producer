package org.bugmakers404.hermes.producer;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "org.bugmakers404.hermes.*" })
public class Application {

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("Australia/Sydney"));
    SpringApplication.run(Application.class, args);
  }
}