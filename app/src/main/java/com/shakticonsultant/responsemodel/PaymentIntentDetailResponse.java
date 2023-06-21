package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentIntentDetailResponse implements Serializable
{

    @SerializedName("paymentIntent")
    @Expose
    private String paymentIntent;

    @SerializedName("ephemeralKey")
    @Expose
    private String ephemeralKey;

    @SerializedName("customer")
    @Expose
    private String customer;

    @SerializedName("important_instructions")
    @Expose
    private String important_instructions;

    @SerializedName("publishableKey")
    @Expose
    private String publishableKey;

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

    public String getPaymentIntent() {
        return paymentIntent;
    }

    public String getPackage_balance() {
        return package_balance;
    }

    public void setPackage_balance(String package_balance) {
        this.package_balance = package_balance;
    }

    public void setPaymentIntent(String id) {
        this.paymentIntent = paymentIntent;
    }

    public String getephemeralKey() {
        return ephemeralKey;
    }

    public void setephemeralKey(String ephemeralKey) {
        this.ephemeralKey = ephemeralKey;
    }

    public String getcustomer() {
        return customer;
    }

    public void setcustomer(String customer) {
        this.customer = customer;
    }

    public String getImportant_instructions() {
        return important_instructions;
    }

    public void setImportant_instructions(String important_instructions) {
        this.important_instructions = important_instructions;
    }

    public String getpublishableKey() {
        return publishableKey;
    }

    public void setpublishableKey(String publishableKey) {
        this.publishableKey = publishableKey;
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
