package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetAcademicDetailDatumResponse implements Serializable
{

    @SerializedName("id")
@Expose
private String id;
 @SerializedName("board_X")
@Expose
private String board_X;

 @SerializedName("passed_year_X")
@Expose
private String passed_year_X;
@SerializedName("percentage_X")
@Expose
private String percentage_X;
@SerializedName("board_XII")
@Expose
private String board_XII;
@SerializedName("passed_year_XII")
@Expose
private String passed_year_XII;
@SerializedName("percentage_XII")
@Expose
private String percentage_XII;
@SerializedName("degree_graduation")
@Expose
private String degree_graduation;
@SerializedName("specialization_graduation")
@Expose
private String specialization_graduation;
@SerializedName("passed_year_graduation")
@Expose
private String passed_year_graduation;
@SerializedName("percentage_graduation")
@Expose
private String percentage_graduation;
@SerializedName("university_graduation")
@Expose
private String university_graduation;
@SerializedName("degree_postgraduation")
@Expose
private String degree_postgraduation;
@SerializedName("specialization_postgraduation")
@Expose
private String specialization_postgraduation;
@SerializedName("passed_year_postgraduation")
@Expose
private String passed_year_postgraduation;
@SerializedName("percentage_postgraduation")
@Expose
private String percentage_postgraduation;

@SerializedName("university_postgraduation")
@Expose
private String university_postgraduation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoard_X() {
        return board_X;
    }

    public void setBoard_X(String board_X) {
        this.board_X = board_X;
    }

    public String getPassed_year_X() {
        return passed_year_X;
    }

    public void setPassed_year_X(String passed_year_X) {
        this.passed_year_X = passed_year_X;
    }

    public String getPercentage_X() {
        return percentage_X;
    }

    public void setPercentage_X(String percentage_X) {
        this.percentage_X = percentage_X;
    }

    public String getBoard_XII() {
        return board_XII;
    }

    public void setBoard_XII(String board_XII) {
        this.board_XII = board_XII;
    }

    public String getPassed_year_XII() {
        return passed_year_XII;
    }

    public void setPassed_year_XII(String passed_year_XII) {
        this.passed_year_XII = passed_year_XII;
    }

    public String getPercentage_XII() {
        return percentage_XII;
    }

    public void setPercentage_XII(String percentage_XII) {
        this.percentage_XII = percentage_XII;
    }

    public String getDegree_graduation() {
        return degree_graduation;
    }

    public void setDegree_graduation(String degree_graduation) {
        this.degree_graduation = degree_graduation;
    }

    public String getSpecialization_graduation() {
        return specialization_graduation;
    }

    public void setSpecialization_graduation(String specialization_graduation) {
        this.specialization_graduation = specialization_graduation;
    }

    public String getPassed_year_graduation() {
        return passed_year_graduation;
    }

    public void setPassed_year_graduation(String passed_year_graduation) {
        this.passed_year_graduation = passed_year_graduation;
    }

    public String getPercentage_graduation() {
        return percentage_graduation;
    }

    public void setPercentage_graduation(String percentage_graduation) {
        this.percentage_graduation = percentage_graduation;
    }

    public String getUniversity_graduation() {
        return university_graduation;
    }

    public void setUniversity_graduation(String university_graduation) {
        this.university_graduation = university_graduation;
    }

    public String getDegree_postgraduation() {
        return degree_postgraduation;
    }

    public void setDegree_postgraduation(String degree_postgraduation) {
        this.degree_postgraduation = degree_postgraduation;
    }

    public String getSpecialization_postgraduation() {
        return specialization_postgraduation;
    }

    public void setSpecialization_postgraduation(String specialization_postgraduation) {
        this.specialization_postgraduation = specialization_postgraduation;
    }

    public String getPassed_year_postgraduation() {
        return passed_year_postgraduation;
    }

    public void setPassed_year_postgraduation(String passed_year_postgraduation) {
        this.passed_year_postgraduation = passed_year_postgraduation;
    }

    public String getPercentage_postgraduation() {
        return percentage_postgraduation;
    }

    public void setPercentage_postgraduation(String percentage_postgraduation) {
        this.percentage_postgraduation = percentage_postgraduation;
    }

    public String getUniversity_postgraduation() {
        return university_postgraduation;
    }

    public void setUniversity_postgraduation(String university_postgraduation) {
        this.university_postgraduation = university_postgraduation;
    }
}
