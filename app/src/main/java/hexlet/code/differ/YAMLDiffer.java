package hexlet.code.differ;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.Parser;
import hexlet.code.utils.Extensions;

import java.io.IOException;
import java.nio.file.Path;

public final class YAMLDiffer extends Differ {

    YAMLDiffer(Path filePath1, Path filePath2) throws IOException {
        super(filePath1, filePath2, new YAMLMapper());
        super.checkFilesExtension(Extensions.YAML);
    }

    @Override
    public String generate() throws IOException {
        return Parser.parse(this);
    }
}
