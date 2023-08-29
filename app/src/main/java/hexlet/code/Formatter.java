package hexlet.code;

import hexlet.code.formatters.JSONFormat;
import hexlet.code.formatters.PlainFormat;
import hexlet.code.formatters.StylishFormat;

import java.io.IOException;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public abstract class Formatter {
    public static Formatter getFormatterByName(String formatName) {
        return switch (formatName) {
            case "plain" -> new PlainFormat();
            case "json" -> new JSONFormat();
            default -> new StylishFormat();
        };
    }

    public abstract String format(List<SimpleEntry<
            SimpleEntry<String, Object>,
            SimpleEntry<String, Object>
            >> diffList) throws IOException;
    protected final String toStringNull(Object obj) {
        return obj == null ? "null" : obj.toString();
    }
}
