package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JobShortListDatumResponse implements Serializable
{


    @SerializedName("job_id")
    @Expose
    private String job_id;
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

 @SerializedName("applied_date")
    @Expose
    private String applied_date;

    public String getApplied_date() {
        return applied_date;
    }

    public void setApplied_date(String applied_date) {
        this.applied_date = applied_date;
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

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
