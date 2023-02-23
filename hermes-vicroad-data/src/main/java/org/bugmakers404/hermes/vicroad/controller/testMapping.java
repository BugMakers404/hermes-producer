package org.bugmakers404.hermes.vicroad.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testMapping {

  @GetMapping(value = "/")
  public String getHello() {
    return "hello world";
  }
}
