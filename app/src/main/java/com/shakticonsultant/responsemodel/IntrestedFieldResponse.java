package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class IntrestedFieldResponse implements Serializable
{

    @SerializedName("success")
@Expose
private boolean success;
    @SerializedName("message")
@Expose
private String message;
    @SerializedName("stream")
    @Expose
    private List<InterestedFiledDatumResponse> stream = null;
    @SerializedName("designation")
    @Expose
    private List<InterestedFiledDatumResponse> designation = null;
    @SerializedName("subject")
    @Expose
    private List<InterestedFiledDatumResponse> subject = null;


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

    public List<InterestedFiledDatumResponse> getStream() {
        return stream;
    }

    public void setStream(List<InterestedFiledDatumResponse> stream) {
        this.stream = stream;
    }

    public List<InterestedFiledDatumResponse> getDesignation() {
        return designation;
    }

    public void setDesignation(List<InterestedFiledDatumResponse> designation) {
        this.designation = designation;
    }

    public List<InterestedFiledDatumResponse> getSubject() {
        return subject;
    }

    public void setSubject(List<InterestedFiledDatumResponse> subject) {
        this.subject = subject;
    }
}
