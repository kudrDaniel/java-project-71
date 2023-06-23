package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Log4j2
public final class Differ {

    private final Path pFilePath1;
    private final Path pFilePath2;

    Differ(Path filePath1, Path filePath2) {
        pFilePath1 = filePath1;
        pFilePath2 = filePath2;
    }

    public String generate() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> fileData1;
        Map<String, Object> fileData2;

        InputStream fileBytes1 = InputStream.nullInputStream();
        InputStream fileBytes2 = InputStream.nullInputStream();

        try {
            fileBytes1 = Files.newInputStream(pFilePath1, StandardOpenOption.READ);
            fileBytes2 = Files.newInputStream(pFilePath2, StandardOpenOption.READ);
            fileData1 = mapper.readValue(fileBytes1, new TypeReference<>() { });
            fileData2 = mapper.readValue(fileBytes2, new TypeReference<>() { });
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
            } else if (fileData1.get(key).equals(fileData2.get(key))) {
                //unchanged
                result.put("  " + key, fileData1.get(key));
            } else {
                //changed
                result.put("- " + key, fileData1.get(key));
                result.put("+ " + key, fileData2.get(key));
            }
        }
        return "{\n" + result.entrySet().stream()
                .map(entry -> "\s\s" + entry.getKey() + ": " + entry.getValue().toString())
                .collect(Collectors.joining("\n")) + "\n}";
    }
}
