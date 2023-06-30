package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Formatter;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

public final class JSONFormat extends Formatter {
    @Override
    public String format(LinkedHashMap<String, Object> objectMap) {
        Set<Entry<String, Object>> jsonObjectSet = new LinkedHashSet<>();
        Set<Entry<String, Object>> entries = objectMap.entrySet();
        for (var entry : entries) {
            jsonObjectSet.add(new SimpleEntry<>(
                    entry.getKey().substring(2),
                    entry.getValue()
            ));
        }
        String jsonFormattedString;
        try {
            jsonFormattedString = new ObjectMapper().writeValueAsString(jsonObjectSet);
//
//            Try to use code below, but Collectors.toMap() throws NullPointerException with 'null' keys or values
//
//            jsonFormattedString = new ObjectMapper().writeValueAsString(
//                    objectMap.entrySet().stream()
//                            .map(entry -> new SimpleEntry<>(
//                                    entry.getKey().substring(2),
//                                    entry.getValue()
//                            ))
//                            .collect(Collectors.toMap(
//                                    SimpleEntry::getKey, SimpleEntry::getValue,
//                                    (oldValue, newValue) -> newValue,
//                                    LinkedHashMap::new
//                            ))
//            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return jsonFormattedString;
    }
}
