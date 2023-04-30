package org.bugmakers404.hermes.producer.vicroad.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class JsonProcessor {
    private final JsonFactory factory;

    public JsonProcessor() {
        factory = new JsonFactory();
    }

    public ZonedDateTime extractTimestampFromContent(String events) {
        return extractFirstNonNullIntervalStart(events, "interval_start");
    }

    private ZonedDateTime extractFirstNonNullIntervalStart(String content, String fieldName) {
        try (JsonParser parser = factory.createParser(content)) {
            JsonToken token;
            while ((token = parser.nextToken()) != null) {
                if (token == JsonToken.FIELD_NAME && fieldName.equals(parser.getCurrentName())) {
                    parser.nextToken(); // Move to the value of the specified field
                    String fieldValue = parser.getValueAsString();
                    if (fieldValue != null && !fieldValue.isEmpty()) {
                        return ZonedDateTime.parse(fieldValue);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
    }
}







