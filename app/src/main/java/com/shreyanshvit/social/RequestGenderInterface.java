package com.shreyanshvit.social;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SJ on 10/08/2017.
 */

public interface RequestGenderInterface {
    @GET("genderAnalysis.php")
    Call<GenderResult> getGenderResult(@Query("bill_no") String bill_no);
}