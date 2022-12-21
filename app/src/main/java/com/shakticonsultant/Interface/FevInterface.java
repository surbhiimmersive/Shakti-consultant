package com.shakticonsultant.Interface;

import com.shakticonsultant.responsemodel.FavouriteResponse;

import retrofit2.Response;

public interface FevInterface {

    void getFavResponse(Response<FavouriteResponse> respnse);
}
