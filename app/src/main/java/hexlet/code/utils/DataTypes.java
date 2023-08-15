package hexlet.code.utils;

import java.util.Arrays;

public enum DataTypes {
    JSON("json"),
    YAML("yml", "yaml");

    private final String[] dataTypesArray;

    DataTypes(String... dataTypes) {
        dataTypesArray = dataTypes;
    }

    public String[] getDataTypesArray() {
        return dataTypesArray;
    }

    public String getFirstExtensionByDataType() {
        return getDataTypesArray()[0];
    }

    public static DataTypes byFileExtension(String dataType) throws IndexOutOfBoundsException {
        return Arrays.stream(values())
                .filter(dataTypes -> Arrays.stream(dataTypes.dataTypesArray)
                        .anyMatch(value -> value.equalsIgnoreCase(dataType)))
                .toList().get(0);
    }
}
