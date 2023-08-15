package hexlet.code;

import hexlet.code.utils.DataTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class DifferTest {

    @ParameterizedTest
    @EnumSource(DataTypes.class)
    public void comparisonTest(DataTypes extension) throws IOException {
        Path filePath1 = getFixturePath("right1." + extension.getFirstExtensionByDataType());
        Path filePath2 = getFixturePath("right2." + extension.getFirstExtensionByDataType());
        String expected1 = readExpectedFromFile(getFixturePath("expected_stylish.txt"));
        String expected2 = readExpectedFromFile(getFixturePath("expected_plain.txt"));
        String expected3 = readExpectedFromFile(getFixturePath("expected_json.txt"));

        String actual0 = Differ.generate(filePath1.toString(), filePath2.toString());
        String actual1 = Differ.generate(filePath1.toString(), filePath2.toString(), "stylish");
        String actual2 = Differ.generate(filePath1.toString(), filePath2.toString(), "plain");
        String actual3 = Differ.generate(filePath1.toString(), filePath2.toString(), "json");

        assertThat(actual0).isEqualTo(expected1);
        assertThat(actual1).isEqualTo(expected1);
        assertThat(actual2).isEqualTo(expected2);
        assertThat(actual3).isEqualTo(expected3);
    }

    @ParameterizedTest
    @EnumSource(DataTypes.class)
    public void wrongFilePathTest(DataTypes extension) {
        String wrongFilePath = Paths.get("missing." + extension.getFirstExtensionByDataType()).toString();

        Class<IOException> expected = IOException.class;
        assertThatThrownBy(() -> Differ.generate(wrongFilePath, wrongFilePath))
                .isInstanceOf(expected);
    }

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName).toAbsolutePath();
    }

    private static String readExpectedFromFile(Path filePath) throws IOException {
        return Files.readString(filePath);
    }
}
