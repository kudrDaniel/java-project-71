package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.utils.Extensions;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;

public abstract class Differ {
    private final ObjectMapper pMapper;
    private final Extensions pExtensions;

    protected Differ(ObjectMapper mapper, Extensions extensions) {
        pMapper = mapper;
        pExtensions = extensions;
    }

    public final String generate(Path filePath1, Path filePath2, String formatName) throws IOException {
        this.checkFilesExtension(filePath1, filePath2);
        LinkedHashMap<String, Object> parsedDiff = Parser.parse(
                filePath1,
                filePath2,
                this.getMapper());
        return Formatter.getFormatterByName(formatName).format(parsedDiff);
    }

    protected final void checkFilesExtension(Path filePath1, Path filePath2) throws IOException {
        String fileExt1 = Utils.getFileExtension(filePath1.getFileName());
        String fileExt2 = Utils.getFileExtension(filePath2.getFileName());
        if (fileExt1.isEmpty() || fileExt2.isEmpty()) {
            throw new IOException("One or both files has empty extension:\n"
                    + "\t" + filePath1.getFileName() + "\n"
                    + "\t" + filePath2.getFileName() + "\n"
            );
        } else if (Arrays.stream(pExtensions.getExtensionArray())
                .noneMatch(ext -> ext.equalsIgnoreCase(fileExt1)
                        && ext.equalsIgnoreCase(fileExt2))) {
            throw new IOException("One or both files has wrong extension\n"
                    + "or both files has different extension\n"
                    + "\t" + filePath1.getFileName() + "\n"
                    + "\t" + filePath2.getFileName() + "\n"
            );
        }
    }

    public final ObjectMapper getMapper() {
        return pMapper;
    }
}
