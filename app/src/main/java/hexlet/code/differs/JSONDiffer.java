package hexlet.code.differs;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Differ;
import hexlet.code.utils.Extensions;

import java.io.IOException;

public final class JSONDiffer extends Differ {
    public JSONDiffer() throws IOException {
        super(new ObjectMapper(), Extensions.JSON);
    }
}
