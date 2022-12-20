package com.shakticonsultant.retrofit;
import com.shakticonsultant.responsemodel.AboutResponse;
import com.shakticonsultant.responsemodel.AnnualResponse;
import com.shakticonsultant.responsemodel.BoardResponse;
import com.shakticonsultant.responsemodel.ChangePasswordResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.EducationResponse;
import com.shakticonsultant.responsemodel.FaqsResponse;
import com.shakticonsultant.responsemodel.FavouriteResponse;
import com.shakticonsultant.responsemodel.ForgotResponse;
import com.shakticonsultant.responsemodel.GetAcademicDetailResponse;
import com.shakticonsultant.responsemodel.GetEmployeeHistoryResponse;
import com.shakticonsultant.responsemodel.GetPersonalInformationResponse;
import com.shakticonsultant.responsemodel.InterestedCategoryResponse;
import com.shakticonsultant.responsemodel.IntrestedFieldResponse;
import com.shakticonsultant.responsemodel.JobAppliedListResponse;
import com.shakticonsultant.responsemodel.JobCategoryResponse;
import com.shakticonsultant.responsemodel.JobDetailDatumResponse;
import com.shakticonsultant.responsemodel.JobDetailResponse;
import com.shakticonsultant.responsemodel.JobSkillResponse;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
import com.shakticonsultant.responsemodel.LoginResponse;
import com.shakticonsultant.responsemodel.OrganizationResponse;
import com.shakticonsultant.responsemodel.OurClientResponse;
import com.shakticonsultant.responsemodel.PackageResponse;
import com.shakticonsultant.responsemodel.ProfileResponse;
import com.shakticonsultant.responsemodel.ScheduleInterviewResponse;
import com.shakticonsultant.responsemodel.ShortListResponse;
import com.shakticonsultant.responsemodel.SignupResponse;
import com.shakticonsultant.responsemodel.SliderResponse;
import com.shakticonsultant.responsemodel.StateResponse;
import com.shakticonsultant.responsemodel.TestimonialResponse;
import com.shakticonsultant.responsemodel.UserCategoryResponse;
import com.shakticonsultant.responsemodel.WorkExpResponse;
import com.shakticonsultant.responsemodel.interestedSkillResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse>callSignInApi(@FieldMap Map<String, String> params);

   @POST("register")
   @FormUrlEncoded
   Call<SignupResponse>callSignUpApi(@FieldMap Map<String, String> params);

   @POST("academicDetails")
   @FormUrlEncoded
   Call<CommonResponse>callAcademyDetailApi(@FieldMap Map<String, String> params);

   @POST("forgotPassword")
   @FormUrlEncoded
   Call<ForgotResponse>callForgotpasswordApi(@FieldMap Map<String, String> params);

   @POST("matchOTP")
   @FormUrlEncoded
   Call<CommonResponse>callMatchOtpApi(@FieldMap Map<String, String> params);

   @POST("changePassword")
   @FormUrlEncoded
   Call<ChangePasswordResponse>callChangePassword(@FieldMap Map<String, String> params);


   @POST("personalInformation")
   @FormUrlEncoded
   Call<CommonResponse>callPersonalInfoApi(@FieldMap Map<String, String> params);

   /*@GET("stateList")
   @FormUrlEncoded
   Call<StateResponse>callStateListApi(@FieldMap Map<String, String> params);
*/

   @POST("personalInformation")
   @Multipart
   Call<CommonResponse> callPersonalInformation(@PartMap Map<String, RequestBody> params,@Part MultipartBody.Part resume_doc,@Part MultipartBody.Part id_proof,@Part MultipartBody.Part profile);


   @GET("stateList")
   Call<StateResponse> callStateListApi();

   @GET("boardList")
   Call<BoardResponse> callBoardListApi();

   @GET("annualSalary")
   Call<AnnualResponse> callAnnualSalary();

   @GET("organizationList")
   Call<OrganizationResponse> callOrganizationList();

   @GET("jobCategory")
   Call<JobCategoryResponse> callJobCategory();

   @GET("packageList")
   Call<PackageResponse> callPackageList();

   @GET("faqList")
   Call<FaqsResponse> callFaqsList();


   @POST("cityList")
   @FormUrlEncoded
   Call<CityResponse>callCityListApi(@FieldMap Map<String, String> params);

   @POST("jobSkill")
   @FormUrlEncoded
   Call<JobSkillResponse>callJobSkillList(@FieldMap Map<String, String> params);

   @POST("jobSkillWiseList")
   @FormUrlEncoded
   Call<JobSkillWiseListResponse>callJobSkillWiseList(@FieldMap Map<String, String> params);

   @POST("jobDetails")
   @FormUrlEncoded
   Call<JobDetailResponse>callJobDetail(@FieldMap Map<String, String> params);

   @POST("interviewSchedules")
   @FormUrlEncoded
   Call<ScheduleInterviewResponse>callInterviewSchedule(@FieldMap Map<String, String> params);

   @POST("educationList")
   @FormUrlEncoded
   Call<EducationResponse>callEducationList(@FieldMap Map<String, String> params);

   @POST("interestedFields")
   @FormUrlEncoded
   Call<IntrestedFieldResponse>callInterestedFiledApi(@FieldMap Map<String, String> params);

   @POST("interestedJobCategory")
   @FormUrlEncoded
   Call<InterestedCategoryResponse>callInterestedFiledCategory(@FieldMap Map<String, String> params);
 @POST("interestedJobSkill")
   @FormUrlEncoded
   Call<interestedSkillResponse>callIntererstedSKill(@FieldMap Map<String, String> params);

   @POST("userInterestedField")
   @FormUrlEncoded
   Call<IntrestedFieldResponse>calluserIdInterestedFiled(@FieldMap Map<String, String> params);

   @POST("employeeHistory")
   @FormUrlEncoded
   Call<CommonResponse>callEmployeeHistory(@FieldMap Map<String, String> params);

   @POST("slider")
   @FormUrlEncoded
   Call<SliderResponse>callSliderApi(@FieldMap Map<String, String> params);

   @POST("latestJob")
   @FormUrlEncoded
   Call<JobSkillWiseListResponse>callLatestJob(@FieldMap Map<String, String> params);

   @POST("appliedJobsList")
   @FormUrlEncoded
   Call<JobAppliedListResponse>callAppliedJob(@FieldMap Map<String, String> params);

   @POST("contactUs")
   @FormUrlEncoded
   Call<CommonResponse>callContactusApi(@FieldMap Map<String, String> params);

   @POST("applyJob")
   @FormUrlEncoded
   Call<CommonResponse>callApplyJob(@FieldMap Map<String, String> params);


   @POST("rejectedJobsList")
   @FormUrlEncoded
   Call<JobAppliedListResponse>callRejectedJobList(@FieldMap Map<String, String> params);

   @POST("jobshortList")
   @FormUrlEncoded
   Call<ShortListResponse>callShortListApi(@FieldMap Map<String, String> params);

 @POST("getPersonalDetails")
   @FormUrlEncoded
   Call<GetPersonalInformationResponse>callgetPersonalInformation(@FieldMap Map<String, String> params);

 @POST("getAcademicDetails")
   @FormUrlEncoded
   Call<GetAcademicDetailResponse>callGetAcademicDetails(@FieldMap Map<String, String> params);

 @POST("getEmployeeHistory")
   @FormUrlEncoded
   Call<GetEmployeeHistoryResponse>callGetEmployeeHistory(@FieldMap Map<String, String> params);

 @POST("jobFilter")
   @FormUrlEncoded
   Call<JobSkillWiseListResponse>callFilterApi(@FieldMap Map<String, String> params);

 @POST("shortlistStatus")
   @FormUrlEncoded
   Call<FavouriteResponse>callFavouriteApi(@FieldMap Map<String, String> params);

 @POST("getProfile")
   @FormUrlEncoded
   Call<ProfileResponse>callgetProfileApi(@FieldMap Map<String, String> params);

 @POST("userCategorySkill")
   @FormUrlEncoded
   Call<UserCategoryResponse>calluserCategorySkill(@FieldMap Map<String, String> params);



    @POST("profileUpdate")
    @Multipart
    Call<CommonResponse> callUpdateProfile(@PartMap Map<String, RequestBody> params,@Part MultipartBody.Part id_proof);

    @GET("allJobs")
   Call<JobSkillWiseListResponse> callAllJobs();

   @GET("testimonial")
   Call<TestimonialResponse> callTestimonialApi();
 @GET("privacy")
   Call<AboutResponse> callPrivacyPolicy();

 @GET("terms")
   Call<AboutResponse> callTermsCondition();

   @GET("workExperience")
   Call<WorkExpResponse> callWorkExperience();

   @GET("allCityList")
   Call<CityResponse> callAllCityList();

   @GET("about")
   Call<AboutResponse> callAboutUsApi();

   @GET("ourClient")
   Call<OurClientResponse> callOurClient();


}