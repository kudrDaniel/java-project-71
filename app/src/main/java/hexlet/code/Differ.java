package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.utils.Extensions;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public final class Differ {
    public static String generate(
            String filePath1,
            String filePath2,
            String format)
            throws IOException {
        Path path1 = Paths.get(filePath1);
        Path path2 = Paths.get(filePath2);

        ObjectMapper actMapper = getActualMapper(path1, path2);

        List<String> fileStringList = Reader.readFiles(path1, path2);

        List<Map<String, Object>> mapList = Parser.parse(fileStringList, actMapper);

        Map<String, Object> rawDiffMap = DiffBuilder.getDifference(mapList);

        Formatter formatter = Formatter.getFormatterByName(format);

        return formatter.format(rawDiffMap);

    }

    public static String generate(
            String filePath1,
            String filePath2)
            throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }

    private static ObjectMapper getActualMapper(Path path1, Path path2) throws IOException {
        Extensions[] filesExt = {
                Extensions.byFileExtension(Utils.getFileExtension(path1)),
                Extensions.byFileExtension(Utils.getFileExtension(path2))
        };

        if (filesExt[0] != filesExt[1]) {
            throw new IOException("wrong extension");
        }

        return switch (Extensions.byFileExtension(Utils.getFileExtension(path1))) {
            case JSON -> new ObjectMapper();
            case YAML -> new YAMLMapper();
        };
    }
}
