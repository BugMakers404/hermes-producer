package org.bugmakers404.hermes.producer.vicroad.service.interfaces;

import org.apache.http.HttpResponse;

public interface EventsCollectionService {

  HttpResponse fetchData(String topic);
}
