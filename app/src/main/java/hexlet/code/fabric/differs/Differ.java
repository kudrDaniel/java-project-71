package hexlet.code.fabric.differs;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Utils;
import hexlet.code.utils.Extensions;
import hexlet.code.utils.FilesHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public abstract class Differ {
    private final Path pFilePath1;
    private final Path pFilePath2;
    private final ObjectMapper pMapper;

    protected Differ(Path filePath1, Path filePath2, ObjectMapper mapper) {
        pFilePath1 = filePath1;
        pFilePath2 = filePath2;
        pMapper = mapper;
    }

    public abstract String generate() throws IOException;

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
                    "or both files has different extension\n" +
                    "\t" + pFilePath1.getFileName() + "\n" +
                    "\t" + pFilePath2.getFileName() + "\n"
            );
        }
    }

    public final Path getFilePath(FilesHelper file) {
        return switch (file) {
            case FILE1 -> pFilePath1;
            case FILE2 -> pFilePath2;
        };
    }

    public final ObjectMapper getMapper() {
        return pMapper;
    }
}
