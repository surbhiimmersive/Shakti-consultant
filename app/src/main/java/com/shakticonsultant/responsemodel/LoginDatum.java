package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginDatum implements Serializable
{

@SerializedName("id")
@Expose
private String id;

@SerializedName("name")
@Expose
private String name;

@SerializedName("mobile")
@Expose
private String mobile;


@SerializedName("personal")
@Expose
private String personal;


@SerializedName("academic")
@Expose
private String academic;


@SerializedName("employee")
@Expose
private String employee;

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}