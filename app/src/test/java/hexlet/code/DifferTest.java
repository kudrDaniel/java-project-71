package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class DifferTest {
    private static final Map<String, Object> RIGHT_MAP_1 = Map.of(
            "host", "hexlet.io",
            "timeout", 50,
            "proxy", "123.234.53.22",
            "follow", false
    );
    private static final Map<String, Object> RIGHT_MAP_2 = Map.of(
            "timeout", 20,
            "verbose", true,
            "host", "hexlet.io"
    );

    private static Path pTestFile1;
    private static Path pTestFile2;

    private ObjectMapper pMapper;
    private Differ pTestDiffer;

    @BeforeAll
    public static void createJsonFiles() throws URISyntaxException {
        Path source = Paths.get(Objects.requireNonNull(DifferTest.class.getResource("/")).toURI());
        pTestFile1 = Paths.get(source.toAbsolutePath() + "/testJson1.json");
        pTestFile2 = Paths.get(source.toAbsolutePath() + "/testJson2.json");
    }

    @BeforeEach
    public void initJsonFiles() throws IOException {
        Files.createFile(pTestFile1);
        Files.createFile(pTestFile2);
        pMapper = new ObjectMapper();
        pTestDiffer = new Differ(pTestFile1, pTestFile2);
    }

    @Test
    public void flatComparisonJsonTest() throws Exception {
        writeTestFiles(
                pMapper.writeValueAsBytes(RIGHT_MAP_1),
                pMapper.writeValueAsBytes(RIGHT_MAP_2)
        );

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

    @Test
    public void emptyComparisonJsonTest() throws Exception {
        writeTestFiles(
                pMapper.writeValueAsBytes(Map.of()),
                pMapper.writeValueAsBytes(Map.of())
        );

        String expected =
                """
                        {

                        }""";
        String actual = pTestDiffer.generate();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void emptyFileJsonTest() throws Exception {
        writeTestFiles(
                pMapper.writeValueAsBytes(""),
                pMapper.writeValueAsBytes("")
        );
        Class<InvalidFormatException> expected = InvalidFormatException.class;
        assertThatThrownBy(pTestDiffer::generate).isInstanceOf(expected);
    }

    @Test
    public void wrongFilePathJsonTest() throws Exception {
        Path wrongFilePath = Paths.get(Objects.requireNonNull(this.getClass().getResource("/")).toURI());
        wrongFilePath = Paths.get(wrongFilePath.toAbsolutePath() + "/missing_file.json");

        Differ wrongPathDiffer = new Differ(wrongFilePath, pTestFile2);
        writeTestFiles(
                pMapper.writeValueAsBytes(""),
                pMapper.writeValueAsBytes("")
        );

        Class<IOException> expected = IOException.class;
        assertThatThrownBy(wrongPathDiffer::generate).isInstanceOf(expected);
    }

    @AfterEach
    public void clearJsonFiles() throws IOException {
        Files.delete(pTestFile1);
        Files.delete(pTestFile2);
    }

    private void writeTestFiles(byte[] fileBytes1, byte[] fileBytes2) throws IOException {
        Files.write(pTestFile1, fileBytes1);
        Files.write(pTestFile2, fileBytes2);
    }
}
