package hexlet.code.differ;

import hexlet.code.differ.factory.Differ;
import hexlet.code.differ.factory.JSONDiffer;

import java.io.IOException;
import java.nio.file.Path;

public final class JSONDifferCreator extends DifferCreator {

    @Override
    public Differ createDiffer(Path filePath1, Path filePath2) throws IOException {
        return new JSONDiffer(filePath1, filePath2);
    }
}
