package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Reader {
    public static String readFile(Path path) throws IOException {
        return Files.readString(path);
    }
}
