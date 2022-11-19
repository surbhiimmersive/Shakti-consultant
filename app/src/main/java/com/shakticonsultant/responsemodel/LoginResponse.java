package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable
{

@SerializedName("message")
@Expose
private String message;

@SerializedName("data")
    @Expose
    private LoginDatum data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginDatum getData() {
        return data;
    }

    public void setData(LoginDatum data) {
        this.data = data;
    }
}