package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import hexlet.code.Formatter;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public final class JSONFormat extends Formatter {
    @Override
    public String format(List<SimpleEntry<
            SimpleEntry<String, Object>,
            SimpleEntry<String, Object>
            >> diffList) {
        String jsonFormattedString;
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializerProvider().setNullKeySerializer(new StdSerializer<Object>((Class<Object>) null) {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                gen.writeFieldName("");
            }
        });
        try {
            jsonFormattedString = mapper.writeValueAsString(diffList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonFormattedString;
    }
}
