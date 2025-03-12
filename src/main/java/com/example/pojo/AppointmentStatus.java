package com.example.pojo;

public enum AppointmentStatus {
    BOOKED("booked"),
    CANCELED("canceled"),
    COMPLETED("completed");

    private final String value;

    AppointmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AppointmentStatus fromValue(String value) {
        for (AppointmentStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
