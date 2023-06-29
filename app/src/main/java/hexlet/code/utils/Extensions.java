package hexlet.code.utils;

import java.util.Arrays;

public enum Extensions {
    JSON("json"),
    YAML("yml", "yaml");

    private final String[] extensionArray;

    Extensions(String... extensions) {
        extensionArray = extensions;
    }

    public String[] getExtensionArray() {
        return extensionArray;
    }

    public String getFirstExtension() {
        return getExtensionArray()[0];
    }

    public static Extensions byFileExtension(String extension) throws IndexOutOfBoundsException {
        return Arrays.stream(values())
                .filter(extensions -> Arrays.stream(extensions.extensionArray)
                        .anyMatch(value -> value.equalsIgnoreCase(extension)))
                .toList().get(0);
    }
}
