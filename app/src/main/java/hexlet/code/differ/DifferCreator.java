package hexlet.code.differ;

import hexlet.code.differ.factory.Differ;

import java.io.IOException;
import java.nio.file.Path;

abstract class DifferCreator {
    public abstract Differ createDiffer(Path filePath1, Path filePath2) throws IOException;
}
