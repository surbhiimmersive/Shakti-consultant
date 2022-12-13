package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetEmployeeHostoryDatumResponse implements Serializable
{

    @SerializedName("id")
@Expose
private String id;
   @SerializedName("current_organisation")
@Expose
private String current_organisation;
  @SerializedName("current_state")
@Expose
private String current_state;
  @SerializedName("current_city")
@Expose
private String current_city;
  @SerializedName("current_date_of_joining")
@Expose
private String current_date_of_joining;
  @SerializedName("current_annual_salary")
@Expose
private String current_annual_salary;
  @SerializedName("current_stream")
@Expose
private String current_stream;
  @SerializedName("first_organisation")
@Expose
private String first_organisation;
  @SerializedName("first_date_of_joining")
@Expose
private String first_date_of_joining;
  @SerializedName("first_date_of_reliving")
@Expose
private String first_date_of_reliving;
  @SerializedName("first_annual_salary")
@Expose
private String first_annual_salary;
 @SerializedName("first_stream")
@Expose
private String first_stream;
 @SerializedName("second_organisation")
@Expose
private String second_organisation;
 @SerializedName("second_date_of_joining")
@Expose
private String second_date_of_joining;
 @SerializedName("second_date_of_reliving")
@Expose
private String second_date_of_reliving;
 @SerializedName("second_stream")
@Expose
private String second_stream;

 @SerializedName("third_organisation")
@Expose
private String third_organisation;
@SerializedName("third_date_of_joining")
@Expose
private String third_date_of_joining;
@SerializedName("third_date_of_reliving")
@Expose
private String third_date_of_reliving;
@SerializedName("third_stream")
@Expose
private String third_stream;

@SerializedName("city")
@Expose
private String city;

@SerializedName("state")
@Expose
private String state;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrent_organisation() {
        return current_organisation;
    }

    public void setCurrent_organisation(String current_organisation) {
        this.current_organisation = current_organisation;
    }

    public String getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(String current_state) {
        this.current_state = current_state;
    }

    public String getCurrent_city() {
        return current_city;
    }

    public void setCurrent_city(String current_city) {
        this.current_city = current_city;
    }

    public String getCurrent_date_of_joining() {
        return current_date_of_joining;
    }

    public void setCurrent_date_of_joining(String current_date_of_joining) {
        this.current_date_of_joining = current_date_of_joining;
    }

    public String getCurrent_annual_salary() {
        return current_annual_salary;
    }

    public void setCurrent_annual_salary(String current_annual_salary) {
        this.current_annual_salary = current_annual_salary;
    }

    public String getCurrent_stream() {
        return current_stream;
    }

    public void setCurrent_stream(String current_stream) {
        this.current_stream = current_stream;
    }

    public String getFirst_organisation() {
        return first_organisation;
    }

    public void setFirst_organisation(String first_organisation) {
        this.first_organisation = first_organisation;
    }

    public String getFirst_date_of_joining() {
        return first_date_of_joining;
    }

    public void setFirst_date_of_joining(String first_date_of_joining) {
        this.first_date_of_joining = first_date_of_joining;
    }

    public String getFirst_date_of_reliving() {
        return first_date_of_reliving;
    }

    public void setFirst_date_of_reliving(String first_date_of_reliving) {
        this.first_date_of_reliving = first_date_of_reliving;
    }

    public String getFirst_annual_salary() {
        return first_annual_salary;
    }

    public void setFirst_annual_salary(String first_annual_salary) {
        this.first_annual_salary = first_annual_salary;
    }

    public String getFirst_stream() {
        return first_stream;
    }

    public void setFirst_stream(String first_stream) {
        this.first_stream = first_stream;
    }

    public String getSecond_organisation() {
        return second_organisation;
    }

    public void setSecond_organisation(String second_organisation) {
        this.second_organisation = second_organisation;
    }

    public String getSecond_date_of_joining() {
        return second_date_of_joining;
    }

    public void setSecond_date_of_joining(String second_date_of_joining) {
        this.second_date_of_joining = second_date_of_joining;
    }

    public String getSecond_date_of_reliving() {
        return second_date_of_reliving;
    }

    public void setSecond_date_of_reliving(String second_date_of_reliving) {
        this.second_date_of_reliving = second_date_of_reliving;
    }

    public String getSecond_stream() {
        return second_stream;
    }

    public void setSecond_stream(String second_stream) {
        this.second_stream = second_stream;
    }

    public String getThird_organisation() {
        return third_organisation;
    }

    public void setThird_organisation(String third_organisation) {
        this.third_organisation = third_organisation;
    }

    public String getThird_date_of_joining() {
        return third_date_of_joining;
    }

    public void setThird_date_of_joining(String third_date_of_joining) {
        this.third_date_of_joining = third_date_of_joining;
    }

    public String getThird_date_of_reliving() {
        return third_date_of_reliving;
    }

    public void setThird_date_of_reliving(String third_date_of_reliving) {
        this.third_date_of_reliving = third_date_of_reliving;
    }

    public String getThird_stream() {
        return third_stream;
    }

    public void setThird_stream(String third_stream) {
        this.third_stream = third_stream;
    }
}
