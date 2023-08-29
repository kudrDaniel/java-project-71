package hexlet.code;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class DiffBuilder {
    public static List<SimpleEntry<
            SimpleEntry<String, Object>,
            SimpleEntry<String, Object>
            >> getDifference(
                    Map<String, Object> data0,
                    Map<String, Object> data1) {

        Set<String> keys = new TreeSet<>();
        keys.addAll(data0.keySet());
        keys.addAll(data1.keySet());

        return keys.stream()
                .map(key -> {
                    SimpleEntry<String, Object> oldPair;
                    SimpleEntry<String, Object> newPair;
                    if (!data0.containsKey(key)) {
                        oldPair = null;
                        newPair = new SimpleEntry<>(key, data1.get(key));
                    } else if (!data1.containsKey(key)) {
                        oldPair = new SimpleEntry<>(key, data0.get(key));
                        newPair = null;
                    } else if (Utils.equalsNullable(data0.get(key), (data1.get(key)))) {
                        oldPair = new SimpleEntry<>(key, data0.get(key));
                        newPair = oldPair;
                    } else {
                        oldPair = new SimpleEntry<>(key, data0.get(key));
                        newPair = new SimpleEntry<>(key, data1.get(key));
                    }
                    return new SimpleEntry<>(oldPair, newPair);
                })
                .toList();
    }
}
