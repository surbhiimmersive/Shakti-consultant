package com.shakticonsultant.retrofit;
import com.shakticonsultant.responsemodel.AnnualResponse;
import com.shakticonsultant.responsemodel.BoardResponse;
import com.shakticonsultant.responsemodel.ChangePasswordResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.EducationResponse;
import com.shakticonsultant.responsemodel.ForgotResponse;
import com.shakticonsultant.responsemodel.IntrestedFieldResponse;
import com.shakticonsultant.responsemodel.LoginResponse;
import com.shakticonsultant.responsemodel.OrganizationResponse;
import com.shakticonsultant.responsemodel.SignupResponse;
import com.shakticonsultant.responsemodel.StateResponse;

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


   @POST("cityList")
   @FormUrlEncoded
   Call<CityResponse>callCityListApi(@FieldMap Map<String, String> params);

   @POST("educationList")
   @FormUrlEncoded
   Call<EducationResponse>callEducationList(@FieldMap Map<String, String> params);

   @POST("interestedFields")
   @FormUrlEncoded
   Call<IntrestedFieldResponse>callInterestedFiledApi(@FieldMap Map<String, String> params);

   @POST("userInterestedField")
   @FormUrlEncoded
   Call<IntrestedFieldResponse>calluserIdInterestedFiled(@FieldMap Map<String, String> params);

   @POST("employeeHistory")
   @FormUrlEncoded
   Call<CommonResponse>callEmployeeHistory(@FieldMap Map<String, String> params);

}