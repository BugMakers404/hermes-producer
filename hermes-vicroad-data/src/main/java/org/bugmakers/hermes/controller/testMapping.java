package org.bugmakers.hermes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testMapping {

  @GetMapping(value = "/hello")
  public String getHello() {
    return "hello world";
  }
}
