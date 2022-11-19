package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ForgotResponse implements Serializable
{

@SerializedName("message")
@Expose
private String message;


@SerializedName("data")
    @Expose
    private ForgotDatumDatum data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ForgotDatumDatum getData() {
        return data;
    }

    public void setData(ForgotDatumDatum data) {
        this.data = data;
    }

}