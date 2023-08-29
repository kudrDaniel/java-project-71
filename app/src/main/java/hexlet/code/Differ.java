package hexlet.code;

import hexlet.code.utils.DataType;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
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

        DataType actualDataType = getActualDataType(path0, path1);

        String fileString0 = Reader.readFile(path0);
        String fileString1 = Reader.readFile(path1);

        Map<String, Object> data0 = Parser.parse(fileString0, actualDataType);
        Map<String, Object> data1 = Parser.parse(fileString1, actualDataType);

        List<SimpleEntry<
                SimpleEntry<String, Object>,
                SimpleEntry<String, Object>
                >> diffList = DiffBuilder.getDifference(data0, data1);

        Formatter formatter = Formatter.getFormatterByName(format);

        return formatter.format(diffList);

    }

    public static String generate(
            String filePath1,
            String filePath2)
            throws IOException {
        return generate(filePath1, filePath2, "stylish");
    }

    private static DataType getActualDataType(Path... paths) throws IOException {
        if (paths.length == 0) {
            throw new RuntimeException("No files to match");
        }
        DataType expected = DataType.getDataTypeByFileExtension(Utils.getFileExtension(paths[0]));
        boolean isAllExtSame = Arrays.stream(paths)
                .map(path -> {
                    try {
                        DataType current = DataType.getDataTypeByFileExtension(Utils.getFileExtension(path));
                        if (current == null) {
                            throw new NullPointerException();
                        }
                        return current;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(ext -> ext.equals(expected))
                .toList().size() == paths.length;
        if (isAllExtSame) {
            return expected;
        }
        throw new RuntimeException("Files must have same extensions");
    }
}
