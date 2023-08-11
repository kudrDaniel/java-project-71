package hexlet.code;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class DiffBuilder {
    public static Map<String, Object> getDifference(List<Map<String, Object>> mapList) {
        Map<String, Object> rawDiffMap = new LinkedHashMap<>();

        Map<String, Object> map1 = mapList.get(0);
        Map<String, Object> map2 = mapList.get(1);

        Set<String> keys = new TreeSet<>();

        keys.addAll(map1.keySet());
        keys.addAll(map2.keySet());

        for (var key : keys) {
            String newKey;
            if (!map1.containsKey(key)) {
                //added
                newKey = key + " added";
                rawDiffMap.put(newKey, map2.get(key));
            } else if (!map2.containsKey(key)) {
                //removed
                newKey = key + " removed";
                rawDiffMap.put(newKey, map1.get(key));
            } else if (Utils.equalsNullable(map1.get(key), (map2.get(key)))) {
                //unchanged
                newKey = key + " unchanged";
                rawDiffMap.put(newKey, map1.get(key));
            } else {
                //changed
                newKey = key + " changedFrom";
                rawDiffMap.put(newKey, map1.get(key));
                newKey = key + " changedTo";
                rawDiffMap.put(newKey, map2.get(key));
            }
        }

        return rawDiffMap;
    }
}
