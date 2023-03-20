package org.bugmakers404.hermes.producer.vicroad;

import java.io.File;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration
@TestPropertySource(locations = "file:/Users/yubos1/Code/Projects/hermes/application.properties")
public class CollectBluetoothTravelTimeTest extends AbstractTestNGSpringContextTests {

  @Value("${vicroad.travel-time.subscription-key}")
  public String bluetoothTravelTimeKey;

  @Test
  public void testGetSingleDataFromVicPlatform() throws Exception {
    File file = new File("/Users/yubos1/Code/Projects/hermes/application.properties");
    HttpClient httpclient = HttpClients.createDefault();
    URIBuilder builder = new URIBuilder(
        "https://data-exchange-api.vicroads.vic.gov.au/bluetooth_data/links/3?expand=coordinates,latest_stats");

    URI uri = builder.build();
    HttpGet request = new HttpGet(uri);
    request.setHeader("Ocp-Apim-Subscription-Key", bluetoothTravelTimeKey);

    HttpResponse response = httpclient.execute(request);
    HttpEntity entity = response.getEntity();
    System.out.println(EntityUtils.toString(entity));

  }

}
