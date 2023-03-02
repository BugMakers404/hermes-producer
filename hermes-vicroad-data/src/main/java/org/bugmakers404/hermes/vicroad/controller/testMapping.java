package org.bugmakers404.hermes.vicroad.controller;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bugmakers404.hermes.vicroad.dataentry.bluetoothRawData.Links;
import org.bugmakers404.hermes.vicroad.service.LinksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class testMapping {

  @NonNull
  private final LinksService linksService;

  @GetMapping(value = "/")
  public String getHello() {
    return "hello world";
  }

  @GetMapping(value = "/data")
  public List<Links> getData() {
    return linksService.getAllCollections();
  }
}
