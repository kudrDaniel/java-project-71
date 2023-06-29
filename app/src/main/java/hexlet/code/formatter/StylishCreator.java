package hexlet.code.formatter;

import hexlet.code.formatter.factory.Formatter;
import hexlet.code.formatter.factory.StylishFormat;

public final class StylishCreator extends FormatterCreator {
    @Override
    public Formatter createFormatter() {
        return new StylishFormat();
    }
}
