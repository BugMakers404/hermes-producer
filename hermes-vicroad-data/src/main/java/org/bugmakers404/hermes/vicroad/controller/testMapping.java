package org.bugmakers404.hermes.vicroad.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testMapping {

  @GetMapping(value = "/hello")
  public String getHelloFromVicRoad() {
    return "hello this is vic road";
  }
}
