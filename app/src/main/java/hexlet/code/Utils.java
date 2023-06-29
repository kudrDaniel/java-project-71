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

    public static boolean equalsNullable(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        } else if (obj1 == null || obj2 == null) {
            return false;
        } else {
            return obj1.equals(obj2);
        }
    }
}
