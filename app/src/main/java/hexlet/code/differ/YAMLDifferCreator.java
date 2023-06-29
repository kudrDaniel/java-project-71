package hexlet.code.differ;

import hexlet.code.differ.factory.Differ;
import hexlet.code.differ.factory.YAMLDiffer;

import java.io.IOException;
import java.nio.file.Path;

public final class YAMLDifferCreator extends DifferCreator {

    @Override
    public Differ createDiffer(Path filePath1, Path filePath2) throws IOException {
        return new YAMLDiffer(filePath1, filePath2);
    }
}
