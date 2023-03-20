package org.bugmakers404.hermes.producer.vicroad;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.testng.annotations.Test;

public class DateTimeTest {

  @Test
  public void dateTimeTransferTest() {
    // your string with offset
    String input = "2023-03-03T11:37:00+11:00";

    // create a DateTimeFormatter for the input string format
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    // parse the input string into an OffsetDateTime object
    OffsetDateTime offsetDateTime = OffsetDateTime.parse(input, formatter);

    // convert the OffsetDateTime to a LocalDateTime without the offset
    LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();

    // use the LocalDateTime as needed
    System.out.println(localDateTime);
  }
}
