package com.glofox.enums;

public enum ClassErrors {
    CLASS_ALREADY_EXIST("The class/es already exist"),
    CLASSES_NULL("Classes are null or empty"),
    NOT_EXISTING_CLASS("The class does not exist"),
    NOT_EXISTING_DATE("The class is not available this date"),
    MEMBER_ALREADY_BOOKED("The member already booked this class");


    private final String description;

    ClassErrors(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}