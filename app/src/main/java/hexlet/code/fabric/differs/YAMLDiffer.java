package hexlet.code.fabric.differs;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.Parser;
import hexlet.code.utils.Extensions;
import hexlet.code.utils.FilesHelper;

import java.io.IOException;
import java.nio.file.Path;

public final class YAMLDiffer extends Differ {
    public YAMLDiffer(Path filePath1, Path filePath2) throws IOException {
        super(filePath1, filePath2, new YAMLMapper());
        super.checkFilesExtension(Extensions.YAML);
    }

    @Override
    public String generate() throws IOException {
        return Parser.parse(
                this.getFilePath(FilesHelper.FILE1),
                this.getFilePath(FilesHelper.FILE2),
                this.getMapper());
    }
}
