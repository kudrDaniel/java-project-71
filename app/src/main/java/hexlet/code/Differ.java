package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class Differ {

    private final String pFilePath1;
    private final String pFilePath2;

    Differ(String filePath1, String filePath2) {
        pFilePath1 = filePath1;
        pFilePath2 = filePath2;
    }

    public String generate() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> fileData1;
        Map<String, Object> fileData2;

        try {
            fileData1 = mapper.readValue(new File(pFilePath1), Map.class);
            fileData2 = mapper.readValue(new File(pFilePath2), Map.class);
        } catch (Exception ex) {
            throw ex;
        }

        Map<String, Object> result = new LinkedHashMap<>();
        Set<String> keys = new TreeSet<>(fileData1.keySet());
        keys.addAll(fileData2.keySet());

        for (String key: keys) {

            if (!fileData1.containsKey(key)) {
                //added
                result.put("+" + key, fileData2.get(key));
            } else if (!fileData2.containsKey(key)) {
                //deleted
                result.put("-" + key, fileData1.get(key));
            } else if (fileData1.get(key).equals(fileData2.get(key))) {
                //unchanged
                result.put(" " + key, fileData1.get(key));
            } else {
                //changed
                result.put("-" + key, fileData1.get(key));
                result.put("+" + key, fileData2.get(key));
            }
        }

        return "{\n" + result.entrySet().stream()
                .map(entry -> "\s\s" + entry.getKey() + ": " + entry.getValue().toString())
                .collect(Collectors.joining("\n")) + "\n}";
    }
}
