package ru.golovina.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCodes {
    EMPTY(""),
    VALIDATION_EXCEPTION("ValidationException"),
    UNKNOWN_EXCEPTION("Unknown Exception"),
    UNSUPPORTED_EXCEPTION("UnsupportedException"),;

    private final String name;

    ErrorCodes(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }

}
