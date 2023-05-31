package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JobDetailDatumResponse implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("job_description")
    @Expose
    private String job_description;

    @SerializedName("important_instructions")
    @Expose
    private String important_instructions;

    @SerializedName("starting_salary")
    @Expose
    private String starting_salary;

    @SerializedName("maximum_salary")
    @Expose
    private String maximum_salary;
    @SerializedName("pay_according")
    @Expose
    private String pay_according;
    @SerializedName("total_positions")
    @Expose
    private String pay_accototal_positionsrding;
  @SerializedName("work_experience")
    @Expose
    private String work_experience;
 @SerializedName("location")
    @Expose
    private String location;
@SerializedName("document_required")
    @Expose
    private String document_required;
@SerializedName("package_balance")
    @Expose
    private String package_balance;

@SerializedName("applied_status")
    @Expose
    private int applied_status;

    public int getApplied_status() {
        return applied_status;
    }

    public void setApplied_status(int applied_status) {
        this.applied_status = applied_status;
    }

    public String getId() {
        return id;
    }

    public String getPackage_balance() {
        return package_balance;
    }

    public void setPackage_balance(String package_balance) {
        this.package_balance = package_balance;
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

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getImportant_instructions() {
        return important_instructions;
    }

    public void setImportant_instructions(String important_instructions) {
        this.important_instructions = important_instructions;
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

    public String getPay_accototal_positionsrding() {
        return pay_accototal_positionsrding;
    }

    public void setPay_accototal_positionsrding(String pay_accototal_positionsrding) {
        this.pay_accototal_positionsrding = pay_accototal_positionsrding;
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

    public String getDocument_required() {
        return document_required;
    }

    public void setDocument_required(String document_required) {
        this.document_required = document_required;
    }
}
