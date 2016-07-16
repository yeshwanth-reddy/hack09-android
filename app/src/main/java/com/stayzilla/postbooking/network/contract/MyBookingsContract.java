package com.stayzilla.postbooking.network.contract;

/**
 * Created by nitesh on 7/16/16.
 */

import com.stayzilla.postbooking.model.MyBookingsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface MyBookingsContract {

    @Headers({
            "Content-Type:application/json"
    })

    @GET("/v1/json/bookinggetall/{token}")
    Call<MyBookingsModel> getMyBookings(@Path("token")String token);



}
