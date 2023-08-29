package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.utils.DataType;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Parser {
    public static Map<String, Object> parse(
            String fileString,
            DataType dataType)
            throws IOException {
        ObjectMapper mapper = switch (dataType) {
            case JSON -> new ObjectMapper();
            case YML -> new YAMLMapper();
        };

        return mapper.readValue(
                fileString,
                new TypeReference<LinkedHashMap<String, Object>>() { }
        );
    }
}
