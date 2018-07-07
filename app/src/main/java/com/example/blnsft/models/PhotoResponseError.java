package com.example.blnsft.models;

public abstract class PhotoResponseError {
    private String status;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [error = " + error + ", status = " + status + "]";
    }
}
