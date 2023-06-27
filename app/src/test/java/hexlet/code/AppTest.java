package hexlet.code;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

public final class AppTest {
    private static final int EXIT_CODE_OK = 0;
    @Test
    public void appRightTest() {
        int actualJson = new CommandLine(new App()).execute("file1.json", "file2.json");
        int actualYaml = new CommandLine(new App()).execute("file1.yml", "file2.yml");

        assertThat(actualJson).isEqualTo(EXIT_CODE_OK);
        assertThat(actualYaml).isEqualTo(EXIT_CODE_OK);
    }
}
