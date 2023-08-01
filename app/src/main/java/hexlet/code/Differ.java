package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class Differ {

    public Differ() {
    }

    public String generate(
            Path filePath1,
            Path filePath2,
            String formatName)
            throws IOException {

        List<Map<String, Object>> mapList = Parser.parse(filePath1, filePath2);

        Map<String, Object> map1 = mapList.get(0);
        Map<String, Object> map2 = mapList.get(1);
        Set<String> keys = new TreeSet<>();
        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());

        Map<String, Object> differMap = new LinkedHashMap<>();

        for (var key : keys) {
            String newKey;
            if (!map1.containsKey(key)) {
                //added
                newKey = key + " added";
                differMap.put(newKey, map2.get(key));
            } else if (!map2.containsKey(key)) {
                //removed
                newKey = key + " removed";
                differMap.put(newKey, map1.get(key));
            } else if (Utils.equalsNullable(map1.get(key), (map2.get(key)))) {
                //unchanged
                newKey = key + " unchanged";
                differMap.put(newKey, map1.get(key));
            } else {
                //changed
                newKey = key + " changedFrom";
                differMap.put(newKey, map1.get(key));
                newKey = key + " changedTo";
                differMap.put(newKey, map2.get(key));
            }
        }
        return Formatter.getFormatterByName(formatName).format(differMap);
    }
}
