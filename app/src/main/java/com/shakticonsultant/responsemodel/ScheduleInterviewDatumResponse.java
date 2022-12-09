package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ScheduleInterviewDatumResponse implements Serializable
{

    @SerializedName("schedule_id")
    @Expose
    private String schedule_id;
 @SerializedName("job_id")
    @Expose
    private String job_id;
 @SerializedName("title")
    @Expose
    private String title;

 @SerializedName("google_meet_link")
    @Expose
    private String google_meet_link;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("schedule_date")
    @Expose
    private String schedule_date;
 @SerializedName("schedule_time")
    @Expose
    private String schedule_time;

 @SerializedName("schedule_day")
    @Expose
    private String schedule_day;

    public String getSchedule_day() {
        return schedule_day;
    }

    public void setSchedule_day(String schedule_day) {
        this.schedule_day = schedule_day;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoogle_meet_link() {
        return google_meet_link;
    }

    public void setGoogle_meet_link(String google_meet_link) {
        this.google_meet_link = google_meet_link;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getSchedule_time() {
        return schedule_time;
    }

    public void setSchedule_time(String schedule_time) {
        this.schedule_time = schedule_time;
    }
}
