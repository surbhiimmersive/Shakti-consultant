package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable
{

@SerializedName("success")
@Expose
private boolean success;

@SerializedName("message")
@Expose
private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("data")
    @Expose
    private LoginDatum data;

    public LoginDatum getData() {
        return data;
    }

    public void setData(LoginDatum data) {
        this.data = data;
    }
}