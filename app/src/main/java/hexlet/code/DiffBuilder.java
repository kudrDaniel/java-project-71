package hexlet.code;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class DiffBuilder {
    public static Map<String, Object> getDifference(List<Map<String, Object>> filesData) {
        Map<String, Object> rawDiff = new LinkedHashMap<>();

        Map<String, Object> data0 = filesData.get(0);
        Map<String, Object> data1 = filesData.get(1);

        Set<String> keys = new TreeSet<>();

        keys.addAll(data0.keySet());
        keys.addAll(data1.keySet());

        for (var key : keys) {
            String newKey;
            if (!data0.containsKey(key)) {
                //added
                newKey = key + " added";
                rawDiff.put(newKey, data1.get(key));
            } else if (!data1.containsKey(key)) {
                //removed
                newKey = key + " removed";
                rawDiff.put(newKey, data0.get(key));
            } else if (Utils.equalsNullable(data0.get(key), (data1.get(key)))) {
                //unchanged
                newKey = key + " unchanged";
                rawDiff.put(newKey, data0.get(key));
            } else {
                //changed
                newKey = key + " changedFrom";
                rawDiff.put(newKey, data0.get(key));
                newKey = key + " changedTo";
                rawDiff.put(newKey, data1.get(key));
            }
        }

        return rawDiff;
    }
}
