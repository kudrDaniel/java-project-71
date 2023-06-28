package hexlet.code.fabric.differs;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Parser;
import hexlet.code.utils.Extensions;
import hexlet.code.utils.FilesHelper;

import java.io.IOException;
import java.nio.file.Path;

public final class JSONDiffer extends Differ {
    public JSONDiffer(Path filePath1, Path filePath2) throws IOException {
        super(filePath1, filePath2, new ObjectMapper());
        super.checkFilesExtension(Extensions.JSON);
    }

    @Override
    public String generate() throws IOException {
        return Parser.parse(
                this.getFilePath(FilesHelper.FILE1),
                this.getFilePath(FilesHelper.FILE2),
                this.getMapper());
    }
}
