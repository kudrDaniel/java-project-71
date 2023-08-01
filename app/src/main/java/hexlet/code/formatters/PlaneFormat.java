package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.Map;

public final class PlaneFormat extends Formatter {
    @Override
    public String format(Map<String, Object> objectMap) {
        StringBuilder result = new StringBuilder();
        objectMap.forEach((key1, value) -> {
            String[] pair = key1.split(" ");
            String key = pair[0];
            String option = pair[1];
            key = switch (option) {
                case "removed" -> "Property '" + key + "' was removed\n";
                case "changedFrom" -> "Property '" + key + "' was updated. From "
                        + getComplexString(value) + " to ";
                case "added" -> "Property '" + key + "' was added with value: "
                        + getComplexString(value) + "\n";
                case "changedTo" -> getComplexString(value) + "\n";
                default -> "";
            };
            result.append(key);
        });
        result.deleteCharAt(result.lastIndexOf("\n"));
        return result.toString();
    }
}
