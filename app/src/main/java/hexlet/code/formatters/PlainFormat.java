package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.StringJoiner;

public final class PlainFormat extends Formatter {
    @Override
    public String format(List<SimpleEntry<
            SimpleEntry<String, Object>,
            SimpleEntry<String, Object>
            >> diffList) {
        return diffList.stream()
                .map(entry -> {
                    String oldPair;
                    String newPair;
                    if (entry.getKey() == null) {
                        oldPair = null;
                        newPair = "Property '" + entry.getValue().getKey()
                                + "' was added with value: "
                                + getComplexString(entry.getValue().getValue());
                    } else if (entry.getValue() == null) {
                        oldPair = "Property '" + entry.getKey().getKey()
                                + "' was removed";
                        newPair = null;
                    } else if (entry.getKey() == entry.getValue()) {
                        oldPair = null;
                        newPair = null;
                    } else {
                        oldPair = "Property '" + entry.getKey().getKey()
                                + "' was updated. From " + getComplexString(entry.getKey().getValue());
                        newPair = " to " + getComplexString(entry.getValue().getValue());
                    }
                    if (oldPair == null || newPair == null) {
                        if (oldPair != null) {
                            return oldPair;
                        } else {
                            return newPair;
                        }
                    } else {
                        return oldPair + newPair;
                    }
                })
                .collect(
                        () -> new StringJoiner("\n"),
                        (joiner, s) -> {
                            if (s != null) {
                                joiner.add(s);
                            }
                        },
                        StringJoiner::merge
                ).toString();
    }

    private String getComplexString(Object obj) {
        if (toStringNull(obj).contains("[") || toStringNull(obj).contains("{")) {
            return "[complex value]";
        } else if (obj instanceof String) {
            return "'" + obj + "'";
        } else {
            return toStringNull(obj);
        }
    }
}
