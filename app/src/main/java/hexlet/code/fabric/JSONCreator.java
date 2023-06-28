package hexlet.code.fabric;

import hexlet.code.fabric.differs.Differ;
import hexlet.code.fabric.differs.JSONDiffer;

import java.io.IOException;
import java.nio.file.Path;

public final class JSONCreator extends Creator {

    @Override
    public Differ createDiffer(Path filePath1, Path filePath2) throws IOException {
        return new JSONDiffer(filePath1, filePath2);
    }
}
