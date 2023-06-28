package hexlet.code;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import hexlet.code.fabric.differs.Differ;
import hexlet.code.fabric.differs.JSONDiffer;
import hexlet.code.fabric.differs.YAMLDiffer;
import hexlet.code.utils.Extensions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class ParserTest {
    private static final List<Map<String, Object>> RIGHT_MAP_LIST = Arrays.asList(
            Map.of(
                    "host", "hexlet.io",
                    "timeout", 50,
                    "proxy", "123.234.53.22",
                    "follow", false
            ),
            Map.of(
                    "timeout", 20,
                    "verbose", true,
                    "host", "hexlet.io"
            )
    );
    private static final List<String> FILES_NAMES = Arrays.asList("testFile1", "testFile2");

    private static final Map<Extensions, List<Path>> TEST_FILES_MAP_BY_EXT = new HashMap<>();

    private Differ pTestDiffer;

    @BeforeAll
    public static void createFiles() throws IOException, URISyntaxException {
        Path source = Paths.get(Objects.requireNonNull(ParserTest.class.getResource("/")).toURI());
        for (var ext : Extensions.values()) {
            List<Path> testFiles = new ArrayList<>();
            for (var fileName : FILES_NAMES) {
                testFiles.add(Paths.get(source.toAbsolutePath() + "/" + fileName + "." + ext.extension[0]));
                Files.createFile(testFiles.get(testFiles.size() - 1));
            }
            TEST_FILES_MAP_BY_EXT.put(ext, testFiles);
        }
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void flatComparisonTest(Extensions extension) throws IOException {
        List<Path> testFiles = TEST_FILES_MAP_BY_EXT.get(extension);
        pTestDiffer = switch (extension) {
            case JSON -> new JSONDiffer(testFiles.get(0), testFiles.get(1));
            case YAML -> new YAMLDiffer(testFiles.get(0), testFiles.get(1));
        };
        for (int i = 0; i < testFiles.size(); i++) {
            Files.write(testFiles.get(i), pTestDiffer.getMapper().writeValueAsBytes(RIGHT_MAP_LIST.get(i)));
        }

        String expected =
                """
                        {
                        \s\s- follow: false
                        \s\s  host: hexlet.io
                        \s\s- proxy: 123.234.53.22
                        \s\s- timeout: 50
                        \s\s+ timeout: 20
                        \s\s+ verbose: true
                        }""";
        String actual = pTestDiffer.generate();
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void emptyComparisonTest(Extensions extension) throws Exception {
        List<Path> testFiles = TEST_FILES_MAP_BY_EXT.get(extension);
        pTestDiffer = switch (extension) {
            case JSON -> new JSONDiffer(testFiles.get(0), testFiles.get(1));
            case YAML -> new YAMLDiffer(testFiles.get(0), testFiles.get(1));
        };
        for (Path testFile : testFiles) {
            Files.write(testFile, pTestDiffer.getMapper().writeValueAsBytes(Map.of()));
        }

        String expected =
                """
                        {

                        }""";
        String actual = pTestDiffer.generate();
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void emptyFileTest(Extensions extension) throws Exception {
        List<Path> testFiles = TEST_FILES_MAP_BY_EXT.get(extension);
        pTestDiffer = switch (extension) {
            case JSON -> new JSONDiffer(testFiles.get(0), testFiles.get(1));
            case YAML -> new YAMLDiffer(testFiles.get(0), testFiles.get(1));
        };
        for (Path testFile : testFiles) {
            Files.write(testFile, pTestDiffer.getMapper().writeValueAsBytes(""));
        }

        Class<InvalidFormatException> expected = InvalidFormatException.class;
        assertThatThrownBy(pTestDiffer::generate).isInstanceOf(expected);
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void wrongFilePathTest(Extensions extension) throws IOException, URISyntaxException {
        Path wrongFilePath = Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).toURI());
        wrongFilePath = Paths.get(wrongFilePath.toAbsolutePath() + "/missing_file." + extension.extension[0]);
        Differ wrongPathDiffer = switch (extension) {
            case JSON -> new JSONDiffer(wrongFilePath, wrongFilePath);
            case YAML -> new YAMLDiffer(wrongFilePath, wrongFilePath);
        };

        Class<IOException> expected = IOException.class;
        assertThatThrownBy(wrongPathDiffer::generate).isInstanceOf(expected);
    }
    /*@ParameterizedTest
    @ValueSource(strings = {".json", ".yml"})
    public void wrongExtensionTest(String extension) {

    }*/

    @AfterAll
    public static void clearFiles() throws IOException {
        for (var ext : Extensions.values()) {
            for (int i = 0; i < FILES_NAMES.size(); i++) {
                Files.delete(TEST_FILES_MAP_BY_EXT.get(ext).get(i));
            }
        }
    }
}
