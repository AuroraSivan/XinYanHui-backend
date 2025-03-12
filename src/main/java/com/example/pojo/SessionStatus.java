package com.example.pojo;

public enum SessionStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    CANCELED("canceled");

    private final String value;

    SessionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SessionStatus fromValue(String value) {
        for (SessionStatus status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid session status value: " + value);
    }
}
