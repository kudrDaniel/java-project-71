package hexlet.code;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import hexlet.code.utils.ExitCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.InvalidPathException;

import static org.assertj.core.api.Assertions.assertThat;

public final class AppTest {
    private final CommandLine commandLine = new CommandLine(new App());
    private final CommandLine.IExecutionExceptionHandler exceptionHandler = (ex, commandLine, parseResult) -> {
        //ex.printStackTrace(); // no stack trace
        commandLine.getErr().println(ex.getMessage());
        //commandLine.usage(commandLine.getErr());
        return commandLine.getExitCodeExceptionMapper().getExitCode(ex);
    };
    private final CommandLine.IExitCodeExceptionMapper exceptionMapper = exception -> {
        if (exception instanceof StreamReadException) {
            return ExitCodes.EXIT_STREAM_READ;
        } else if (exception instanceof DatabindException) {
            return ExitCodes.EXIT_DATABIND;
        } else if (exception instanceof InvalidPathException) {
            return ExitCodes.EXIT_INVALID_PATH;
        } else if (exception instanceof IOException) {
            if (exception.getMessage().contains("no extension")) {
                return ExitCodes.EXIT_FILE_NO_EXTENSION;
            } else if (exception.getMessage().contains("empty extension")) {
                return ExitCodes.EXIT_FILE_EMPTY_EXTENSION;
            } else if (exception.getMessage().contains("wrong extension")) {
                return ExitCodes.EXIT_FILE_WRONG_EXTENSION;
            } else {
                return ExitCodes.EXIT_FILE_OPEN_CLOSE_STREAM;
            }
        } else {
            return ExitCodes.EXIT_WRONG;
        }
    };

    @BeforeEach
    public void appInti(){
        commandLine
                .setExitCodeExceptionMapper(exceptionMapper)
                .setExecutionExceptionHandler(exceptionHandler);
    }

    @Test
    public void appRightTest() {
        int actualJson = commandLine.execute("file1.json", "file2.json");
        int actualYaml = commandLine.execute("file1.yml", "file2.yml");

        assertThat(actualJson).isEqualTo(ExitCodes.EXIT_OK);
        assertThat(actualYaml).isEqualTo(ExitCodes.EXIT_OK);
    }

    @Test
    public void appErrorTest() {
        int errorJson = commandLine.execute("file1", "file2.json");
        int errorYaml = commandLine.execute("file1", "file2.yml");

        assertThat(errorJson).isEqualTo(ExitCodes.EXIT_FILE_NO_EXTENSION);
        assertThat(errorYaml).isEqualTo(ExitCodes.EXIT_FILE_NO_EXTENSION);
    }
}
