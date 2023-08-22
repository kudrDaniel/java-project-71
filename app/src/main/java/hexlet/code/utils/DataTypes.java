package hexlet.code.utils;

import java.util.Arrays;

public enum DataTypes {
    JSON("json"),
    YAML("yml", "yaml");

    private final String[] dataTypes;

    DataTypes(String... dataTypes) {
        this.dataTypes = dataTypes;
    }

    public String[] getDataTypes() {
        return dataTypes;
    }

    public String getFirstExtensionByDataType() {
        return getDataTypes()[0];
    }

    public static DataTypes byFileExtension(String dataType) throws IndexOutOfBoundsException {
        return Arrays.stream(values())
                .filter(dataTypes -> Arrays.stream(dataTypes.dataTypes)
                        .anyMatch(value -> value.equalsIgnoreCase(dataType)))
                .toList().get(0);
    }
}
