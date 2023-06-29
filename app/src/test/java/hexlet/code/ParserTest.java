package hexlet.code;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import hexlet.code.differs.JSONDiffer;
import hexlet.code.differs.YAMLDiffer;
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

    private final Path resources = Paths.get(
            Objects.requireNonNull(this.getClass().getClassLoader().getResource("parserTest")).toURI());

    private Differ pTestDiffer;

    public ParserTest() throws URISyntaxException {
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void stylishComparisonTest(Extensions extension) throws IOException {
        List<Path> testFiles = List.of(
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "right1." + extension.getFirstExtension()),
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "right2." + extension.getFirstExtension())
        );
        pTestDiffer = switch (extension) {
            case JSON -> new JSONDiffer();
            case YAML -> new YAMLDiffer();
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
        String actual = pTestDiffer.generate(testFiles.get(0), testFiles.get(1), "stylish");
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void plainComparisonTest(Extensions extension) throws IOException {
        List<Path> testFiles = List.of(
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "right1." + extension.getFirstExtension()),
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        "right2." + extension.getFirstExtension())
        );
        pTestDiffer = switch (extension) {
            case JSON -> new JSONDiffer();
            case YAML -> new YAMLDiffer();
        };
        String expected =
                """
                        Property 'chars2' was updated. From [complex value] to false
                        Property 'checked' was updated. From false to true
                        Property 'default' was updated. From null to [complex value]
                        Property 'id' was updated. From 45 to null
                        Property 'key1' was removed
                        Property 'key2' was added with value: 'value2'
                        Property 'numbers2' was updated. From [complex value] to [complex value]
                        Property 'numbers3' was removed
                        Property 'numbers4' was added with value: [complex value]
                        Property 'obj1' was added with value: [complex value]
                        Property 'setting1' was updated. From 'Some value' to 'Another value'
                        Property 'setting2' was updated. From 200 to 300
                        Property 'setting3' was updated. From true to 'none'""";
        String actual = pTestDiffer.generate(testFiles.get(0), testFiles.get(1), "plane");
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
            case JSON -> new JSONDiffer();
            case YAML -> new YAMLDiffer();
        };
        for (Path testFile : testFiles) {
            Files.write(testFile, pTestDiffer.getMapper().writeValueAsBytes(Map.of()));
        }

        String expected =
                """
                        {

                        }""";
        String actual = pTestDiffer.generate(testFiles.get(0), testFiles.get(1), "stylish");
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
            case JSON -> new JSONDiffer();
            case YAML -> new YAMLDiffer();
        };
        for (Path testFile : testFiles) {
            Files.write(testFile, pTestDiffer.getMapper().writeValueAsBytes(""));
        }

        Class<InvalidFormatException> expected = InvalidFormatException.class;
        assertThatThrownBy(() -> pTestDiffer.generate(testFiles.get(0), testFiles.get(1), "stylish"))
                .isInstanceOf(expected);
    }

    /*@ParameterizedTest
    @ValueSource(strings = {".json", ".yml"})
    public void wrongExtensionTest(String extension) {

    }*/
    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void wrongFilePathTest(Extensions extension) throws IOException {
        Path wrongFilePath = Paths.get("missing." + extension.getFirstExtension());
        pTestDiffer = switch (extension) {
            case JSON -> new JSONDiffer();
            case YAML -> new YAMLDiffer();
        };

        Class<IOException> expected = IOException.class;
        assertThatThrownBy(() -> pTestDiffer.generate(wrongFilePath, wrongFilePath, "stylish"))
                .isInstanceOf(expected);
    }
}
