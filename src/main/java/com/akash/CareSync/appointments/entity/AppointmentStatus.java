package com.akash.CareSync.appointments.entity;

public enum AppointmentStatus {
    REQUESTED(0),      // 0 - Appointment requested
    CONFIRMED(1),      // 1 - Appointment confirmed
    WAITING(2),        // 2 - Waiting list
    CANCELLED(3),      // 3 - Appointment cancelled
    COMPLETED(4),      // 4 - Appointment completed
    DELETED(5);        // 5 - Appointment deleted

    private final int statusCode;

    AppointmentStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static AppointmentStatus fromStatusCode(int statusCode) {
        for (AppointmentStatus status : AppointmentStatus.values()) {
            if (status.getStatusCode() == statusCode) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + statusCode);
    }
}
