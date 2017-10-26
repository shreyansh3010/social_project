package com.shreyanshvit.social;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface Billinvoice {

    @FormUrlEncoded()
    @POST("/bill.php")
    public void senBill(
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("uid") String uid,
            @Field("name") String name,
            @Field("city") String city,
            @Field("dist") String dist,
            @Field("state") String state,
            @Field("address") String address,
            @Field("gender") String gender,
            @Field("age") String age,
            @Field("product") String product,
            @Field("total") String total,
            @Field("cat") String cat,
            Callback<Response> response
    );
}