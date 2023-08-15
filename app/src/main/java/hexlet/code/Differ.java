package hexlet.code;

import hexlet.code.utils.DataTypes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Differ {
    public static String generate(
            String filePath1,
            String filePath2,
            String format)
            throws IOException {
        Path path0 = Paths.get(filePath1).toAbsolutePath();
        Path path1 = Paths.get(filePath2).toAbsolutePath();

        DataTypes actualDataType = getActualDataType(path0, path1);

        String fileString0 = Reader.readFile(path0);
        String fileString1 = Reader.readFile(path1);

        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(Parser.parse(fileString0, actualDataType));
        mapList.add(Parser.parse(fileString1, actualDataType));

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

    private static DataTypes getActualDataType(Path path1, Path path2) throws IOException {
        DataTypes[] filesExt = {
                DataTypes.byFileExtension(Utils.getFileExtension(path1)),
                DataTypes.byFileExtension(Utils.getFileExtension(path2))
        };

        if (filesExt[0] != filesExt[1]) {
            throw new IOException("wrong extension");
        }

        return DataTypes.byFileExtension(Utils.getFileExtension(path1));
    }
}
