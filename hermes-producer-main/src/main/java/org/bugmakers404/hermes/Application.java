package org.bugmakers404.hermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.bugmakers.hermes.vicroad.controller"})
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}