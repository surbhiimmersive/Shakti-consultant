package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JobSkillDatumResponse implements Serializable
{

    @SerializedName("category_id")
    @Expose
    private String category_id;
    @SerializedName("id")
    @Expose
    private String id;
 @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("icon")
    @Expose
    private String icon;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
