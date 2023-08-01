package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Formatter;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class JSONFormat extends Formatter {
    @Override
    public String format(Map<String, Object> objectMap) {
        Set<Entry<String, Object>> jsonObjectSet = new LinkedHashSet<>();
        Set<Entry<String, Object>> entries = objectMap.entrySet();
        for (var entry : entries) {
            var key = entry.getKey().split(" ")[0];
            jsonObjectSet.add(new SimpleEntry<>(
                    key,
                    entry.getValue()
            ));
        }
        String jsonFormattedString;
        try {
            jsonFormattedString = new ObjectMapper().writeValueAsString(jsonObjectSet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return jsonFormattedString;
    }
}
