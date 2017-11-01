package com.shreyanshvit.social.Fragments.CheckBill.RequestInterface;

/**
 * Created by SJ on 10/08/2017.
 */

import com.shreyanshvit.social.Fragments.CheckBill.JSONResponse.JSONResponse;
import com.shreyanshvit.social.Fragments.CheckBill.JSONResponse.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

    @GET("getbill2.php")
    Call<JSONResponse> getJSON();
}