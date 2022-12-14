package com.shakticonsultant.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetPersonalInformationDatumResponse implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("categoryname")
    @Expose
    private String categoryname;

    @SerializedName("skillname")
    @Expose
    private String skillname;

    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
   @SerializedName("profile_image")
   @Expose
   private String profile_image;
   @SerializedName("name_prefix")
   @Expose
     private String name_prefix;

   @SerializedName("gender")
   @Expose
     private String gender;
   @SerializedName("date_of_birth")
   @Expose
     private String date_of_birth;

   @SerializedName("state_id")
   @Expose
     private String state_id;
   @SerializedName("city_id")
   @Expose
     private String city_id;

   @SerializedName("alternate_mobile")
   @Expose
     private String alternate_mobile;
   @SerializedName("resume")
   @Expose
     private String resume;

   @SerializedName("id_proof")
   @Expose
     private String id_proof;
   @SerializedName("lecture_video_link")
   @Expose
     private String lecture_video_link;

   @SerializedName("interested_field")
   @Expose
     private String interested_field;

   @SerializedName("division")
   @Expose
     private String division;
   @SerializedName("stream")
   @Expose
     private String stream;
   @SerializedName("subject")
   @Expose
     private String subject;

   @SerializedName("experience")
   @Expose
     private String experience;
   @SerializedName("organization_name")
   @Expose
     private String organization_name;
   @SerializedName("first_job_month")
   @Expose
     private String first_job_month;

   @SerializedName("first_job_year")
   @Expose
     private String first_job_year;
   @SerializedName("annual_salary")
   @Expose
     private String annual_salary;
   @SerializedName("category_id")
   @Expose
     private String category_id;
   @SerializedName("are_you_working_with_these_group")
   @Expose
     private String are_you_working_with_these_group;

   @SerializedName("working_organization")
   @Expose
     private String working_organization;
   @SerializedName("are_you_worked_with_these_group")
   @Expose
     private String are_you_worked_with_these_group;

   @SerializedName("worked_organization")
   @Expose
     private String worked_organization;
 @SerializedName("skill_id")
   @Expose
     private String skill_id;

    public String getCategory_id() {
        return category_id;
    }

    public String getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(String skill_id) {
        this.skill_id = skill_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getSkillname() {
        return skillname;
    }

    public void setSkillname(String skillname) {
        this.skillname = skillname;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getName_prefix() {
        return name_prefix;
    }

    public void setName_prefix(String name_prefix) {
        this.name_prefix = name_prefix;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getAlternate_mobile() {
        return alternate_mobile;
    }

    public void setAlternate_mobile(String alternate_mobile) {
        this.alternate_mobile = alternate_mobile;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getId_proof() {
        return id_proof;
    }

    public void setId_proof(String id_proof) {
        this.id_proof = id_proof;
    }

    public String getLecture_video_link() {
        return lecture_video_link;
    }

    public void setLecture_video_link(String lecture_video_link) {
        this.lecture_video_link = lecture_video_link;
    }

    public String getInterested_field() {
        return interested_field;
    }

    public void setInterested_field(String interested_field) {
        this.interested_field = interested_field;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public String getFirst_job_month() {
        return first_job_month;
    }

    public void setFirst_job_month(String first_job_month) {
        this.first_job_month = first_job_month;
    }

    public String getFirst_job_year() {
        return first_job_year;
    }

    public void setFirst_job_year(String first_job_year) {
        this.first_job_year = first_job_year;
    }

    public String getAnnual_salary() {
        return annual_salary;
    }

    public void setAnnual_salary(String annual_salary) {
        this.annual_salary = annual_salary;
    }

    public String getAre_you_working_with_these_group() {
        return are_you_working_with_these_group;
    }

    public void setAre_you_working_with_these_group(String are_you_working_with_these_group) {
        this.are_you_working_with_these_group = are_you_working_with_these_group;
    }

    public String getWorking_organization() {
        return working_organization;
    }

    public void setWorking_organization(String working_organization) {
        this.working_organization = working_organization;
    }

    public String getAre_you_worked_with_these_group() {
        return are_you_worked_with_these_group;
    }

    public void setAre_you_worked_with_these_group(String are_you_worked_with_these_group) {
        this.are_you_worked_with_these_group = are_you_worked_with_these_group;
    }

    public String getWorked_organization() {
        return worked_organization;
    }

    public void setWorked_organization(String worked_organization) {
        this.worked_organization = worked_organization;
    }
}
