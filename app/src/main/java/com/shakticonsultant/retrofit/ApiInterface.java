package com.shakticonsultant.retrofit;
import com.shakticonsultant.responsemodel.AnnualResponse;
import com.shakticonsultant.responsemodel.ChangePasswordResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.ForgotResponse;
import com.shakticonsultant.responsemodel.IntrestedFieldResponse;
import com.shakticonsultant.responsemodel.LoginResponse;
import com.shakticonsultant.responsemodel.OrganizationResponse;
import com.shakticonsultant.responsemodel.SignupResponse;
import com.shakticonsultant.responsemodel.StateResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login")
    @FormUrlEncoded
    Call<LoginResponse>callSignInApi(@FieldMap Map<String, String> params);

   @POST("register")
   @FormUrlEncoded
   Call<SignupResponse>callSignUpApi(@FieldMap Map<String, String> params);

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

   @GET("stateList")
   Call<StateResponse> callStateListApi();

   @GET("annualSalary")
   Call<AnnualResponse> callAnnualSalary();

   @GET("organizationList")
   Call<OrganizationResponse> callOrganizationList();


   @POST("cityList")
   @FormUrlEncoded
   Call<CityResponse>callCityListApi(@FieldMap Map<String, String> params);

   @POST("interestedFields")
   @FormUrlEncoded
   Call<IntrestedFieldResponse>callInterestedFiledApi(@FieldMap Map<String, String> params);

}