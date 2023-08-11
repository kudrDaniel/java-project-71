package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Formatter;

import java.util.Map;

public final class JSONFormat extends Formatter {
    @Override
    public String format(Map<String, Object> objectMap) {
        String jsonFormattedString;
        try {
            jsonFormattedString = new ObjectMapper().writeValueAsString(objectMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonFormattedString;
    }
}
