package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EducationDatumResponse implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
 @SerializedName("degree_title")
    @Expose
    private String degree_title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDegree_title() {
        return degree_title;
    }

    public void setDegree_title(String degree_title) {
        this.degree_title = degree_title;
    }
}
