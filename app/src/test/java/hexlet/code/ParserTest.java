package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import hexlet.code.utils.Extensions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class ParserTest {

    private final Path resources = Paths.get(
            Objects.requireNonNull(this.getClass().getClassLoader().getResource("parserTest")).toURI());

    private final Map<String, Path> expectedFiles = Map.of(
            "stylish", Paths.get(resources.toAbsolutePath().toString(),
                    "expected",
                    "stylishExpected.txt"),
            "plain", Paths.get(resources.toAbsolutePath().toString(),
                    "expected",
                    "plainExpected.txt"),
            "json", Paths.get(resources.toAbsolutePath().toString(),
                    "expected",
                    "jsonExpected.txt"),
            "blank", Paths.get(resources.toAbsolutePath().toString(),
                    "expected",
                    "blankExpected.txt")
    );

    private final Differ pTestDiffer = new Differ();

    public ParserTest() throws URISyntaxException {
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void comparisonTest(Extensions extension) throws IOException {
        List<Path> testFiles = generateFilePaths("right1", "right2", extension);

        String expected = readExpectedFromFileByKey("stylish");
        String actual = pTestDiffer.generate(testFiles.get(0), testFiles.get(1), "stylish");
        assertThat(actual).isEqualTo(expected);

        expected = readExpectedFromFileByKey("plain");
        actual = pTestDiffer.generate(testFiles.get(0), testFiles.get(1), "plain");
        assertThat(actual).isEqualTo(expected);

        expected = readExpectedFromFileByKey("json");
        actual = pTestDiffer.generate(testFiles.get(0), testFiles.get(1), "json");
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void emptyComparisonTest(Extensions extension) throws Exception {
        List<Path> testFiles = generateFilePaths("blank1", "blank2", extension);
        for (Path testFile : testFiles) {
            Files.write(testFile, new ObjectMapper().writeValueAsBytes(Map.of()));
        }

        String expected = readExpectedFromFileByKey("blank");
        String actual = pTestDiffer.generate(testFiles.get(0), testFiles.get(1), "stylish");
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void emptyFileTest(Extensions extension) throws Exception {
        List<Path> testFiles = generateFilePaths("empty1", "empty2", extension);
        for (Path testFile : testFiles) {
            Files.write(testFile, new ObjectMapper().writeValueAsBytes(""));
        }

        Class<InvalidFormatException> expected = InvalidFormatException.class;
        assertThatThrownBy(() -> pTestDiffer.generate(testFiles.get(0), testFiles.get(1), "stylish"))
                .isInstanceOf(expected);
    }

    @ParameterizedTest
    @EnumSource(Extensions.class)
    public void wrongFilePathTest(Extensions extension) {
        Path wrongFilePath = Paths.get("missing." + extension.getFirstExtension());

        Class<IOException> expected = IOException.class;
        assertThatThrownBy(() -> pTestDiffer.generate(wrongFilePath, wrongFilePath, "stylish"))
                .isInstanceOf(expected);
    }

    private List<Path> generateFilePaths(String name1, String name2, Extensions extension) {
        return List.of(
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        name1 + "." + extension.getFirstExtension()),
                Paths.get(resources.toAbsolutePath().toString(),
                        extension.getFirstExtension(),
                        name2 + "." + extension.getFirstExtension())
        );
    }

    private String readExpectedFromFileByKey(String key) throws IOException {
        InputStream inputStream = Files.newInputStream(expectedFiles.get(key), StandardOpenOption.READ);
        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
