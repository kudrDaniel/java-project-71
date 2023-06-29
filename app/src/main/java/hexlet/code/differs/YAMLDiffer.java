package hexlet.code.differs;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.Differ;
import hexlet.code.utils.Extensions;

import java.io.IOException;

public final class YAMLDiffer extends Differ {
    public YAMLDiffer() throws IOException {
        super(new YAMLMapper(), Extensions.YAML);
    }
}
