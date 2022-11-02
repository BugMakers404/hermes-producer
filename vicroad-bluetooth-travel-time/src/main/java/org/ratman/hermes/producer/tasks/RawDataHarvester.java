package org.ratman.hermes.producer.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableAsync
@Configuration
public class RawDataHarvester {

//  @Async
//  @Scheduled(fixedDelay = 2000)
//  public void myTasks1() throws InterruptedException {
//    CloseableHttpClient httpclient = HttpClients.createDefault();
//    try {
//      URIBuilder builder = new URIBuilder("https://data-exchange-api.vicroads.vic.gov.au/bluetooth_data/links");
//      URI uri = builder.build();
//      HttpGet request = new HttpGet(uri);
//      request.setHeader("Ocp-Apim-Subscription-Key", "488f4599180941be86276225a6661d44");
//
//      HttpResponse response = httpclient.execute(request);
//      HttpEntity entity = response.getEntity();
//
//      if (entity != null) {
//        JSONArray objects = new JSONArray(EntityUtils.toString(entity));
//        for (int i = 0; i < 2; i++) {
//          System.out.println(i);
//          System.out.println(objects.get(i));
//        }
//      }
//
//
//    } catch (Exception e) {
//      System.out.println(e.getMessage().length());
//    }
//  }

}
