package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public final class Utils {
    public static String getFileExtension(String fileName) throws IOException {
        return Optional.ofNullable(fileName)
                .filter(string -> string.contains("."))
                .map(string -> string.substring(string.lastIndexOf('.') + 1))
                .orElseThrow(() -> new IOException("File has no extension:\n" +
                        "\t" + fileName + "\n"));
    }

    public static String getFileExtension(Path filePath) throws IOException {
        return getFileExtension(filePath.getFileName().toString());
    }
}
