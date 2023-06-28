package hexlet.code.fabric;

import hexlet.code.fabric.differs.Differ;

import java.io.IOException;
import java.nio.file.Path;

abstract class Creator {
    public abstract Differ createDiffer(Path filePath1, Path filePath2) throws IOException;
}
