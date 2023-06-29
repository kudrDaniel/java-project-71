package hexlet.code;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import hexlet.code.differ.factory.Differ;
import hexlet.code.differ.factory.JSONDiffer;
import hexlet.code.differ.factory.YAMLDiffer;
import hexlet.code.formatter.factory.Formatter;
import hexlet.code.formatter.factory.StylishFormat;
import hexlet.code.utils.Extensions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class ParserTest {
    private static final Formatter TEST_FORMATTER = new StylishFormat();

    private final Path resources = Paths.get(
            Objects.requireNonNull(this.getClass().getClassLoader().getResource("parserTest")).toURI());

    private Differ pTestDiffer;

    public ParserTest() throws URISyntaxException {
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void rightComparisonTest(Extensions extension) throws IOException {
        List<Path> testFiles = List.of(
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "right1." + extension.getFirstExtension()),
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "right2." + extension.getFirstExtension())
        );
        pTestDiffer = switch (extension) {
            case JSON -> new JSONDiffer(testFiles.get(0), testFiles.get(1));
            case YAML -> new YAMLDiffer(testFiles.get(0), testFiles.get(1));
        };

        String expected =
                """
                        {
                            chars1: [a, b, c]
                          - chars2: [d, e, f]
                          + chars2: false
                          - checked: false
                          + checked: true
                          - default: null
                          + default: [value1, value2]
                          - id: 45
                          + id: null
                          - key1: value1
                          + key2: value2
                            numbers1: [1, 2, 3, 4]
                          - numbers2: [2, 3, 4, 5]
                          + numbers2: [22, 33, 44, 55]
                          - numbers3: [3, 4, 5]
                          + numbers4: [4, 5, 6]
                          + obj1: {nestedKey=value, isNested=true}
                          - setting1: Some value
                          + setting1: Another value
                          - setting2: 200
                          + setting2: 300
                          - setting3: true
                          + setting3: none
                        }""";
        String actual = TEST_FORMATTER.format(pTestDiffer.generate());
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void emptyComparisonTest(Extensions extension) throws Exception {
        List<Path> testFiles = List.of(
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "blank1." + extension.getFirstExtension()),
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "blank2." + extension.getFirstExtension())
        );
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
        String actual = TEST_FORMATTER.format(pTestDiffer.generate());
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void emptyFileTest(Extensions extension) throws Exception {
        List<Path> testFiles = List.of(
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "empty1." + extension.getFirstExtension()),
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "empty2." + extension.getFirstExtension())
        );
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

    /*@ParameterizedTest
    @ValueSource(strings = {".json", ".yml"})
    public void wrongExtensionTest(String extension) {

    }*/
    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void wrongFilePathTest(Extensions extension) throws IOException, URISyntaxException {
        Path wrongFilePath = Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).toURI());
        wrongFilePath = Paths.get(
                wrongFilePath.toAbsolutePath().toString(),
                "missing_file." + extension.getFirstExtension()
        );
        Differ wrongPathDiffer = switch (extension) {
            case JSON -> new JSONDiffer(wrongFilePath, wrongFilePath);
            case YAML -> new YAMLDiffer(wrongFilePath, wrongFilePath);
        };

        Class<IOException> expected = IOException.class;
        assertThatThrownBy(wrongPathDiffer::generate).isInstanceOf(expected);
    }
}
