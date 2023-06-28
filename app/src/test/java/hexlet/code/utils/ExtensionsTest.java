package hexlet.code.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExtensionsTest {
    @Test
    public void extensionTest() {
        Extensions expectedJSON = Extensions.JSON;
        Extensions expectedYAML = Extensions.YAML;

        Extensions actual1 = Extensions.byFileExtension("json");
        Extensions actual2 = Extensions.byFileExtension("yaml");
        Extensions actual3 = Extensions.byFileExtension("yml");

        assertThat(actual1).isEqualByComparingTo(expectedJSON);
        assertThat(actual2).isEqualByComparingTo(expectedYAML);
        assertThat(actual3).isEqualByComparingTo(expectedYAML);

        IndexOutOfBoundsException expectedWrong = new IndexOutOfBoundsException();

        assertThatThrownBy(() -> Extensions.byFileExtension("exe"))
                .isInstanceOf(expectedWrong.getClass());
    }
}
