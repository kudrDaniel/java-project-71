package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public final class PlaneFormat extends Formatter {
    @Override
    public String format(LinkedHashMap<String, Object> objectMap) {
        StringBuilder sBuilder = new StringBuilder();
        LinkedHashMap<Entry<String, Object>, String> newObjectMap = objectMap.entrySet().stream()
                .filter(entry -> entry.getKey().charAt(0) != ' ')
                .map(entry -> {
                    String newKey = switch (entry.getKey().substring(0, 1)) {
                        case "-" -> "removed";
                        case "+" -> "added";
                        case "1" -> "updatedFrom";
                        case "2" -> "updatedTo";
                        default -> null;
                    };
                    SimpleEntry<String, Object> newPair = new SimpleEntry<>(
                            entry.getKey().substring(2),
                            entry.getValue());
                    return new SimpleEntry<>(newPair, newKey);
                }).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue,
                        (oldEntry, newEntry) -> newEntry,
                        LinkedHashMap::new));
        for (var pair : newObjectMap.keySet()) {
            String toAdd = switch (newObjectMap.get(pair)) {
                case "removed" -> "Property '" + pair.getKey() + "' was removed\n";
                case "added" -> "Property '" + pair.getKey() + "' was added with value: "
                + getComplexString(pair.getValue()) + "\n";
                case "updatedFrom" -> "Property '" + pair.getKey() + "' was updated. From "
                + getComplexString(pair.getValue()) + " to ";
                case "updatedTo" -> getComplexString(pair.getValue()) + "\n";
                default -> "Bruh";
            };
            sBuilder.append(toAdd);
        }
        sBuilder.deleteCharAt(sBuilder.lastIndexOf("\n"));
        return sBuilder.toString();
    }
}
