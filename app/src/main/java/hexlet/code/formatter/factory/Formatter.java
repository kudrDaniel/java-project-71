package hexlet.code.formatter.factory;

import java.util.Map;

public abstract class Formatter {
    public abstract String format(Map<String, Object> objectMap);
    public final String toStringNull(Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}
