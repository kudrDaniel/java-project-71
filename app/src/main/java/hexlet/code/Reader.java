package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Reader {
    public static List<String> readFiles(Path path1, Path path2) throws IOException {
        List<String> fileStringList = new ArrayList<>();

        fileStringList.add(Files.readString(path1));
        fileStringList.add(Files.readString(path2));

        return fileStringList;
    }
}
