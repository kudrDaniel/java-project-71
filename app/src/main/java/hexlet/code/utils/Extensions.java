package hexlet.code.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Extensions {
    JSON("json"),
    YAML("yaml", "yml");

    public final String[] extension;

    public static Extensions byFileExtension(String extension) throws IndexOutOfBoundsException {
        return Arrays.stream(values())
                .filter(extensions -> Arrays.stream(extensions.extension)
                        .anyMatch(value -> value.equalsIgnoreCase(extension)))
                .toList().get(0);
    }

    Extensions(String... extension) {
        this.extension = extension;
    }
}
