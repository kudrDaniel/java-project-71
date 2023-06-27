package hexlet.code;

import hexlet.code.differ.Differ;
import hexlet.code.utils.Extensions;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

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

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "format";

    @Override
    public Integer call() throws Exception {
        Path fullFilePath1 = Paths.get(filepath1);
        Path fullFilePath2 = Paths.get(filepath2);

        Differ differ = Differ.createDiffer(
                fullFilePath1,
                fullFilePath2,
                Extensions.byFileExtension(Utils.getFileExtension(fullFilePath1)));
        String difference;
        difference = differ.generate();

        System.out.println();
        System.out.println(difference);

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
