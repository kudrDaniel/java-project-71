package hexlet.code;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

public final class AppTest {
    private static final int EXIT_CODE_OK = 0;
    @Test
    public void appRightTest() {
        int actual1 = new CommandLine(new App()).execute("file1.json", "file2.json");
        assertThat(actual1).isEqualTo(EXIT_CODE_OK);
    }
}
