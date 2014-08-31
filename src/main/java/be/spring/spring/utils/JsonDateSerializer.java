package be.spring.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by u0090265 on 5/11/14.
 */
@Component
public class JsonDateSerializer extends JsonSerializer<DateTime> {
    private static DateTimeFormatter formatter =
            DateTimeFormat.forPattern("dd-MM-yyyy");

    @Override
    public void serialize(DateTime value, JsonGenerator gen,
                          SerializerProvider arg2)
            throws IOException, JsonProcessingException {

        gen.writeString(formatter.print(value));
    }

}
