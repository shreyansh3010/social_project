package com.shreyanshvit.social;

import com.shreyanshvit.social.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by SJ on 10/08/2017.
 */

public interface RequestInterface1 {
    @GET("getproduct.php")
    Call<Result> getResult(@Query("product_id") String product_id);
}