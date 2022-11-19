package com.shakticonsultant.utils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GetInterface {

    String JSONGETURL = "http://thegraphicplanet.com/kaary_india/api/user/";

    @GET("login")
    Call<String> getUserLogin( @QueryMap Map<String, String> options);
}