package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationDatumResponse implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
 @SerializedName("message")
    @Expose
    private String message;

 @SerializedName("type")
    @Expose
    private String type;
@SerializedName("date_time")
    @Expose
    private String date_time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
