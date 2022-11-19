package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignupResponse implements Serializable
{

@SerializedName("message")
@Expose
private String message;

    @SerializedName("data")
    @Expose
    private SignupDatum data;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignupDatum getData() {
        return data;
    }

    public void setData(SignupDatum data) {
        this.data = data;
    }
}