package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class UtilsTest {
    private static final Map<String, List<Path>> pathMapByParam;

    static {
        try {
            pathMapByParam = Map.of(
                    "right", List.of(
                            Paths.get(Paths.get(Objects.requireNonNull(UtilsTest.class.getResource("/")).toURI()).toAbsolutePath() + "/file.yaml"),
                            Paths.get("file.exe"),
                            Paths.get(".gitignore")
                    ),
                    "empty", List.of(
                            Paths.get(""),
                            Paths.get("asd.")
                    )
            );
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void utilsTest() throws IOException{
        List<String> rightExpected = List.of("yaml", "exe", "gitignore");
        for (int i = 0; i < rightExpected.size(); i++) {
            assertThat(Utils.getFileExtension(pathMapByParam.get("right").get(i))).isEqualTo(rightExpected.get(i));
        }
        IOException wrongExpected = new IOException("no extension");
        for (Path path : pathMapByParam.get("empty")) {
            assertThatThrownBy(() -> Utils.getFileExtension(path))
                    .isInstanceOf(wrongExpected.getClass())
                    .hasMessageContaining(wrongExpected.getMessage());
        }
    }
}
