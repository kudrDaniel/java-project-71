package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public final class StylishFormat extends Formatter {
    @Override
    public String format(LinkedHashMap<String, Object> objectMap) {
        return "{\n" + objectMap.entrySet().stream()
                .map(entry -> {
                    String key = switch (entry.getKey().substring(0, 1)) {
                        case "1" -> "-" + entry.getKey().substring(1);
                        case "2" -> "+" + entry.getKey().substring(1);
                        default -> entry.getKey();
                    };
                    return "\s\s" + key + ": " + toStringNull(entry.getValue());
                })
                .collect(Collectors.joining("\n")) + "\n}";
    }
}
