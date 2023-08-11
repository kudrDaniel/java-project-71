package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Parser {
    public static List<Map<String, Object>> parse(
            List<String> fileStringList,
            ObjectMapper mapper)
            throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();

        Map<String, Object> objectMap1 = mapper.readValue(fileStringList.get(0),
                new TypeReference<HashMap<String, Object>>() {
                });
        Map<String, Object> objectMap2 = mapper.readValue(fileStringList.get(1),
                new TypeReference<HashMap<String, Object>>() {
                });
        mapList.add(objectMap1);
        mapList.add(objectMap2);

        return mapList;
    }
}
