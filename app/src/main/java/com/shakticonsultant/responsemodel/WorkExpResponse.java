package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WorkExpResponse implements Serializable
{

    @SerializedName("success")
@Expose
private boolean success;
    @SerializedName("message")
@Expose
private String message;
    @SerializedName("data")
    @Expose
    private List<WorkExpDatumResponse> data = null;
    private final static long serialVersionUID = -6706837348014011869L;

    public List<WorkExpDatumResponse> getData() {
        return data;
    }


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

    public void setData(List<WorkExpDatumResponse> data) {
        this.data = data;
    }
}
