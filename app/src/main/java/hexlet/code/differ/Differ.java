package hexlet.code.differ;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Utils;
import hexlet.code.utils.Extensions;
import hexlet.code.utils.FilesHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public abstract class Differ {
    protected final Path pFilePath1;
    protected final Path pFilePath2;
    protected final ObjectMapper pMapper;

    protected Differ(Path filePath1, Path filePath2, ObjectMapper mapper) {
        pFilePath1 = filePath1;
        pFilePath2 = filePath2;
        pMapper = mapper;
    }

    public abstract String generate() throws IOException;

    public final Path getFilePath(FilesHelper file) {
        return switch (file) {
            case FILE1 -> pFilePath1;
            case FILE2 -> pFilePath2;
        };
    }

    protected final void checkFilesExtension(Extensions extensions) throws IOException {
        String fileExt1 = Utils.getFileExtension(pFilePath1.getFileName());
        String fileExt2 = Utils.getFileExtension(pFilePath2.getFileName());
        if (fileExt1.isEmpty() || fileExt2.isEmpty()) {
            throw new IOException("One or both files has empty extension:\n" +
                    "\t" + pFilePath1.getFileName() + "\n" +
                    "\t" + pFilePath2.getFileName() + "\n"
            );
        } else if (Arrays.stream(extensions.extension)
                .noneMatch(ext -> ext.equalsIgnoreCase(fileExt1) &&
                        ext.equalsIgnoreCase(fileExt2))) {
            throw new IOException("One or both files has wrong extension\n" +
                    "or both files has different extension" +
                    "\t" + pFilePath1.getFileName() + "\n" +
                    "\t" + pFilePath2.getFileName() + "\n"
            );
        }
    }

    public final ObjectMapper getMapper() {
        return pMapper;
    }

    public static Differ createDiffer(Path path1, Path path2, Extensions ext) throws IOException {
        return switch (ext) {
            case JSON -> new JSONDiffer(path1, path2);
            case YAML -> new YAMLDiffer(path1, path2);
        };
    }
}
