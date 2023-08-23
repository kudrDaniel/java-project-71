package hexlet.code.utils;

public enum DataType {
    JSON("json"),
    YML("yml");

    private final String dataType;

    DataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public static DataType getDataTypeByFileExtension(String extension) {
        return switch (extension) {
            case "json" -> DataType.JSON;
            case "yaml", "yml" -> DataType.YML;
            default -> null;
        };
    }
}
