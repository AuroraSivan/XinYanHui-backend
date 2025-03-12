package com.example.pojo;

public enum ConSessionStatus {
    ACTIVE("active"),
    COMPLETED("completed"),
    TERMINATED("terminated");

    private final String value;

    ConSessionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // 可选：从字符串值转换为枚举的方法
    public static ConSessionStatus fromValue(String value) {
        for (ConSessionStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}