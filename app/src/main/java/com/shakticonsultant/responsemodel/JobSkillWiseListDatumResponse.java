package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JobSkillWiseListDatumResponse implements Serializable
{


    @SerializedName("id")
    @Expose
    private String id;
  @SerializedName("title")
    @Expose
    private String title;
 @SerializedName("starting_salary")
    @Expose
    private String starting_salary;
 @SerializedName("maximum_salary")
    @Expose
    private String maximum_salary;
 @SerializedName("pay_according")
    @Expose
    private String pay_according;
 @SerializedName("work_experience")
    @Expose
    private String work_experience;
 @SerializedName("location")
    @Expose
    private String location;
@SerializedName("is_fav")
    @Expose
    private int is_fav;

    public int getIs_fav() {
        return is_fav;
    }

    public void setIs_fav(int is_fav) {
        this.is_fav = is_fav;
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

    public String getStarting_salary() {
        return starting_salary;
    }

    public void setStarting_salary(String starting_salary) {
        this.starting_salary = starting_salary;
    }

    public String getMaximum_salary() {
        return maximum_salary;
    }

    public void setMaximum_salary(String maximum_salary) {
        this.maximum_salary = maximum_salary;
    }

    public String getPay_according() {
        return pay_according;
    }

    public void setPay_according(String pay_according) {
        this.pay_according = pay_according;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
