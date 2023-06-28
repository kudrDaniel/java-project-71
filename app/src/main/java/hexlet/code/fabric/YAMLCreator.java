package hexlet.code.fabric;

import hexlet.code.fabric.differs.Differ;
import hexlet.code.fabric.differs.YAMLDiffer;

import java.io.IOException;
import java.nio.file.Path;

public final class YAMLCreator extends Creator {

    @Override
    public Differ createDiffer(Path filePath1, Path filePath2) throws IOException {
        return new YAMLDiffer(filePath1, filePath2);
    }
}
