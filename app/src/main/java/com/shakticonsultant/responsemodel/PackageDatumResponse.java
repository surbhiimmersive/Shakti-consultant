package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PackageDatumResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("package_id")
    @Expose
    private int package_id;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("package_start_date")
    @Expose
    private String package_start_date;
    @SerializedName("package_end_date")
    @Expose
    private String package_end_date;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("subscription_days")
    @Expose
    private String subscription_days;
    @SerializedName("bgcolor")
    @Expose
    private String bgcolor;
    @SerializedName("no_of_jobs")
    @Expose
    private String no_of_jobs;
    @SerializedName("package_balance")
    @Expose
    private String package_balance;

    public String getPackage_balance() {
        return package_balance;
    }

    public void setPackage_balance(String package_balance) {
        this.package_balance = package_balance;
    }

    public String getNo_of_jobs() {
        return no_of_jobs;
    }

    public String getDescription() {
        return description;
    }

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public void setNo_of_jobs(String no_of_jobs) {
        this.no_of_jobs = no_of_jobs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getPackage_end_date() {
        return package_end_date;
    }

    public String getPackage_start_date() {
        return package_start_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubscription_days() {
        return subscription_days;
    }

    public void setSubscription_days(String subscription_days) {
        this.subscription_days = subscription_days;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }
}
