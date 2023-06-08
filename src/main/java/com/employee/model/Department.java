package com.employee.model;

public enum Department {
    IT("Information Technology"),
    HR("Human Resources"),
    SALES("Sales");

    private final String displayName;

    Department(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
