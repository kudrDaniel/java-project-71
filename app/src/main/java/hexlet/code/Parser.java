package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.utils.Extensions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Parser {
    public static List<Map<String, Object>> parse(
            String filePath1,
            String filePath2)
            throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();

        InputStream fileBytes1 = InputStream.nullInputStream();
        InputStream fileBytes2 = InputStream.nullInputStream();

        Path path1 = Paths.get(filePath1);
        Path path2 = Paths.get(filePath2);

        Extensions[] filesExt = {
                Extensions.byFileExtension(Utils.getFileExtension(path1)),
                Extensions.byFileExtension(Utils.getFileExtension(path2))
        };

        if (filesExt[0] != filesExt[1]) {
            throw new IOException("wrong extension");
        }

        ObjectMapper mapper = switch (Extensions.byFileExtension(Utils.getFileExtension(path1))) {
            case JSON -> new ObjectMapper();
            case YAML -> new YAMLMapper();
        };

        try {
            fileBytes1 = Files.newInputStream(path1, StandardOpenOption.READ);
            fileBytes2 = Files.newInputStream(path2, StandardOpenOption.READ);

            Map<String, Object> objectMap1 = mapper.readValue(fileBytes1,
                    new TypeReference<HashMap<String, Object>>() {
                    });
            Map<String, Object> objectMap2 = mapper.readValue(fileBytes2,
                    new TypeReference<HashMap<String, Object>>() {
                    });

            mapList.add(objectMap1);
            mapList.add(objectMap2);
        } finally {
            fileBytes1.close();
            fileBytes2.close();
        }
        return mapList;
    }
}
