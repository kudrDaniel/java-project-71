package hexlet.code;

import hexlet.code.formatters.JSONFormat;
import hexlet.code.formatters.PlaneFormat;
import hexlet.code.formatters.StylishFormat;

import java.io.IOException;
import java.util.Map;

public abstract class Formatter {
    public static Formatter getFormatterByName(String formatName) {
        return switch (formatName) {
            case "stylish" -> new StylishFormat();
            case "plane" -> new PlaneFormat();
            case "json" -> new JSONFormat();
            default -> new StylishFormat();
        };
    }

    public abstract String format(Map<String, Object> objectMap) throws IOException;
    public final String toStringNull(Object obj) {
        return obj == null ? "null" : obj.toString();
    }

    public final String getComplexString(Object obj) {
        if (toStringNull(obj).contains("[") || toStringNull(obj).contains("{")) {
            return "[complex value]";
        } else if (obj instanceof String) {
            return "'" + obj + "'";
        } else {
            return toStringNull(obj);
        }
    }
}
