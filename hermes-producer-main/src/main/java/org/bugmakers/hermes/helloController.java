package org.bugmakers.hermes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

  @GetMapping(value = "/")
  public String hello() {
    return "hello world";
  }
}
