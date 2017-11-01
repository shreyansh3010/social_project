package com.shreyanshvit.social;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SJ on 10/08/2017.
 */

public interface RequestAgeInterface {
    @GET("ageAnalysis.php")
    Call<AgeResult> getAge(@Query("bill_no") String bill_no);
}