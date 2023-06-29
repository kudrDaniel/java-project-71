package hexlet.code.formatter.factory;

import java.util.Map;
import java.util.stream.Collectors;

public final class StylishFormat extends Formatter {
    @Override
    public String format(Map<String, Object> objectMap) {
        return "{\n" + objectMap.entrySet().stream()
                .map(entry -> "\s\s" + entry.getKey() + ": " + toStringNull(entry.getValue()))
                .collect(Collectors.joining("\n")) + "\n}";
    }
}
