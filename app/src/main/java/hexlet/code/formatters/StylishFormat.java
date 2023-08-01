package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.Map;
import java.util.stream.Collectors;

public final class StylishFormat extends Formatter {
    @Override
    public String format(Map<String, Object> objectMap) {
        String start = "{\n";
        String formatted = objectMap.entrySet().stream()
                .map(entry -> {
                    String[] pair = entry.getKey().split(" ");
                    String key = pair[0];
                    String option = pair[1];
                    key = switch (option) {
                        case "removed", "changedFrom" -> "- " + key;
                        case "added", "changedTo" -> "+ " + key;
                        default -> "\s\s" + key;
                    };
                    return "\s\s" + key + ": " + toStringNull(entry.getValue());
                })
                .collect(Collectors.joining("\n"));
        String end = "\n}";
        return start + formatted + end;
    }
}
