package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public final class Parser {
    public static Map<String, Object> parse(Path filePath1, Path filePath2, ObjectMapper mapper) throws IOException {
        Map<String, Object> fileData1;
        Map<String, Object> fileData2;

        InputStream fileBytes1 = InputStream.nullInputStream();
        InputStream fileBytes2 = InputStream.nullInputStream();

        try {
            fileBytes1 = Files.newInputStream(filePath1, StandardOpenOption.READ);
            fileBytes2 = Files.newInputStream(filePath2, StandardOpenOption.READ);
            fileData1 = mapper.readValue(fileBytes1,
                    new TypeReference<LinkedHashMap<String, Object>>() { });
            fileData2 = mapper.readValue(fileBytes2,
                    new TypeReference<LinkedHashMap<String, Object>>() { });
        } finally {
            fileBytes1.close();
            fileBytes2.close();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        Set<String> keys = new TreeSet<>(fileData1.keySet());
        keys.addAll(fileData2.keySet());

        for (String key : keys) {

            if (!fileData1.containsKey(key)) {
                //added
                result.put("+ " + key, fileData2.get(key));
            } else if (!fileData2.containsKey(key)) {
                //deleted
                result.put("- " + key, fileData1.get(key));
            } else if (Utils.equalsNullable(fileData1.get(key), fileData2.get(key))) {
                //unchanged
                result.put("  " + key, fileData1.get(key));
            } else {
                //changed
                result.put("- " + key, fileData1.get(key));
                result.put("+ " + key, fileData2.get(key));
            }
        }
        return result;
    }
}
