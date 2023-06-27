package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class ParserTest {
//    private static final List<Map<String, Object>> RIGHT_MAP_LIST = Arrays.asList(
//            Map.of(
//                    "host", "hexlet.io",
//                    "timeout", 50,
//                    "proxy", "123.234.53.22",
//                    "follow", false
//            ),
//            Map.of(
//                    "timeout", 20,
//                    "verbose", true,
//                    "host", "hexlet.io"
//            )
//    );
//    private static final String JSON_EXT = ".json";
//    private static final String YAML_EXT = ".yml";
//    private static final List<String> EXTENSIONS = Arrays.asList(JSON_EXT, YAML_EXT);
//    private static final List<String> FILES_NAMES = Arrays.asList("testFile1", "testFile2");
//
//    private static final Map<String, List<Path>> TEST_FILES_MAP_BY_EXT = new HashMap<>();
//
//    private ObjectMapper pMapper;
//    private Parser pTestParser;
//
//    @BeforeAll
//    public static void createFiles() throws IOException, URISyntaxException {
//        Path source = Paths.get(Objects.requireNonNull(ParserTest.class.getResource("/")).toURI());
//        for (var ext : EXTENSIONS) {
//            List<Path> testFiles = new ArrayList<>();
//            for (var fileName : FILES_NAMES) {
//                testFiles.add(Paths.get(source.toAbsolutePath() + "/" + fileName + ext));
//                Files.createFile(testFiles.get(testFiles.size() - 1));
//            }
//            TEST_FILES_MAP_BY_EXT.put(ext, testFiles);
//        }
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {".json", ".yml"})
//    public void flatComparisonTest(String extension) throws IOException {
//        List<Path> testFiles = TEST_FILES_MAP_BY_EXT.get(extension);
//        pMapper = getObjectMapperByExtension(extension);
//        for (int i = 0; i < testFiles.size(); i++) {
//            Files.write(testFiles.get(i), pMapper.writeValueAsBytes(RIGHT_MAP_LIST.get(i)));
//        }
//        pTestParser = new Parser(testFiles.get(0), testFiles.get(1));
//
//        String expected =
//                """
//                        {
//                        \s\s- follow: false
//                        \s\s  host: hexlet.io
//                        \s\s- proxy: 123.234.53.22
//                        \s\s- timeout: 50
//                        \s\s+ timeout: 20
//                        \s\s+ verbose: true
//                        }""";
//        String actual = pTestParser.generate();
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {".json", ".yml"})
//    public void emptyComparisonTest(String extension) throws Exception {
//        List<Path> testFiles = TEST_FILES_MAP_BY_EXT.get(extension);
//        pMapper = getObjectMapperByExtension(extension);
//        for (Path testFile : testFiles) {
//            Files.write(testFile, pMapper.writeValueAsBytes(Map.of()));
//        }
//        pTestParser = new Parser(testFiles.get(0), testFiles.get(1));
//
//        String expected =
//                """
//                        {
//
//                        }""";
//        String actual = pTestParser.generate();
//        assertThat(actual).isEqualTo(expected);
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {".json", ".yml"})
//    public void emptyFileTest(String extension) throws Exception {
//        List<Path> testFiles = TEST_FILES_MAP_BY_EXT.get(extension);
//        pMapper = getObjectMapperByExtension(extension);
//        for (Path testFile : testFiles) {
//            Files.write(testFile, pMapper.writeValueAsBytes(""));
//        }
//        pTestParser = new Parser(testFiles.get(0), testFiles.get(1));
//
//        Class<InvalidFormatException> expected = InvalidFormatException.class;
//        assertThatThrownBy(pTestParser::generate).isInstanceOf(expected);
//    }
//
//    @Test
//    public void wrongFilePathTest() throws URISyntaxException {
//        Path wrongFilePath = Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).toURI());
//        wrongFilePath = Paths.get(wrongFilePath.toAbsolutePath() + "/missing_file.json");
//        Parser wrongPathParser = new Parser(wrongFilePath, wrongFilePath);
//
//        Class<IOException> expected = IOException.class;
//        assertThatThrownBy(wrongPathParser::generate).isInstanceOf(expected);
//    }
//    /*@ParameterizedTest
//    @ValueSource(strings = {".json", ".yml"})
//    public void wrongExtensionTest(String extension) {
//
//    }*/
//
//    @AfterEach
//    public void clearFiles() throws IOException {
//        for (var ext : EXTENSIONS) {
//            for (int i = 0; i < FILES_NAMES.size(); i++) {
//                Files.delete(TEST_FILES_MAP_BY_EXT.get(ext).get(i));
//            }
//        }
//    }
//
//    private ObjectMapper getObjectMapperByExtension(String extension) {
//        return switch (extension) {
//            case JSON_EXT -> new ObjectMapper();
//            case YAML_EXT -> new YAMLMapper();
//            default -> null;
//        };
//    }
}
