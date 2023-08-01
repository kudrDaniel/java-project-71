package hexlet.code;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import hexlet.code.utils.ExitCodes;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 0.1",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {
    @Parameters(index = "0", description = "path to first file")
    private String filepath1;
    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format"},
            defaultValue = "stylish",
            description = "output format [default: ${DEFAULT-VALUE}]")
    private String format;

    @Override
    public Integer call() throws Exception {
        Path fullFilePath1 = Paths.get(filepath1);
        Path fullFilePath2 = Paths.get(filepath2);

        Differ differ = new Differ();
        String difference;
        difference = differ.generate(fullFilePath1, fullFilePath2, format);

        System.out.println();
        System.out.println(difference);

        return ExitCodes.EXIT_OK;
    }

    public static void main(String[] args) {
        CommandLine.IExecutionExceptionHandler exceptionHandler = (ex, commandLine, parseResult) -> {
            //ex.printStackTrace(); // no stack trace
            commandLine.getErr().println(ex.getMessage());
            //commandLine.usage(commandLine.getErr());
            return commandLine.getExitCodeExceptionMapper().getExitCode(ex);
        };
        CommandLine.IExitCodeExceptionMapper exceptionMapper = exception -> {
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
        int exitCode = new CommandLine(new App())
                .setExitCodeExceptionMapper(exceptionMapper)
                .setExecutionExceptionHandler(exceptionHandler)
                .execute(args);
        System.exit(exitCode);
    }
}
