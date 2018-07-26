package com.example.blnsft.data.network.models;

public class ValidError {

    private String message;
    private String field;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", field = " + field + "]";
    }
}
