package com.example.blnsft.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class AccountResponseError {

    private String status;
    private String error;
    @SerializedName("valid")
    private ValidError[] validError;

    public ValidError[] getValidError() {
        return validError;
    }

    public void setValidError(ValidError[] validError) {
        this.validError = validError;
    }

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
        return "ClassPojo [validError = " + Arrays.toString(validError) + ", error = " + error + ", status = " + status + "]";
    }
}
