package org.ratman.hermes.producer.Controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProducerController {

  @NonNull
  KafkaTemplate<String, String> kafkaTemplate;

  @RequestMapping("/test")
  public String data(String msg) {
    kafkaTemplate.send("test", msg);
    return "ok";
  }

}
