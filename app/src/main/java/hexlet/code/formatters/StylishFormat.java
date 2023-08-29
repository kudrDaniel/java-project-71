package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

public final class StylishFormat extends Formatter {
    @Override
    public String format(List<SimpleEntry<
            SimpleEntry<String, Object>,
            SimpleEntry<String, Object>
            >> diffList) {
        return "{\n" +  diffList.stream()
                .map(entry -> {
                    String oldPair;
                    String newPair;
                    if (entry.getKey() == null) {
                        oldPair = null;
                        newPair = "+ " + entry.getValue().getKey()
                                + ": " + toStringNull(entry.getValue().getValue());
                    } else if (entry.getValue() == null) {
                        oldPair = "- " + entry.getKey().getKey()
                                + ": " + toStringNull(entry.getKey().getValue());
                        newPair = null;
                    } else if (entry.getKey() == entry.getValue()) {
                        oldPair = null;
                        newPair = "\s\s" + entry.getKey().getKey()
                                + ": " + entry.getKey().getValue();
                    } else {
                        oldPair = "- " + entry.getKey().getKey()
                                + ": " + toStringNull(entry.getKey().getValue());
                        newPair = "+ " + entry.getValue().getKey()
                                + ": " + toStringNull(entry.getValue().getValue());
                    }
                    return (oldPair == null ? "" : "\s\s" + oldPair + "\n")
                            + (newPair == null ? "" : "\s\s" + newPair + "\n");
                })
                .collect(Collectors.joining()) + "}";
    }
}
