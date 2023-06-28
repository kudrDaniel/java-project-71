package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public final class Utils {
    public static String getFileExtension(Path filePath) throws IOException {
        String fileName = filePath.getFileName().toString();
        return Optional.of(fileName)
                .filter(string -> string.contains("."))
                .map(string -> string.substring(string.lastIndexOf('.') + 1))
                .filter(string -> !string.equals(""))
                .orElseThrow(() -> new IOException("File has no extension:\n"
                        + "\t" + fileName + "\n"));
    }
}
