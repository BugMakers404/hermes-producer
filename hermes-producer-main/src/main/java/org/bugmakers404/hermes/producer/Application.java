package org.bugmakers404.hermes.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"org.bugmakers404.hermes.*"})
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}