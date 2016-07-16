package com.stayzilla.postbooking.network.contract;

import com.stayzilla.postbooking.model.EventsRequest;
import com.stayzilla.postbooking.model.PlacesModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by sasikumar on 16/07/16.
 */
public interface EventContract {

    @Headers({
            "Accept: application/json, text/javascript, /; q=0.01",
            "Content-Type: application/json; charset=UTF-8",
            "X-Requested-With: XMLHttpRequest",
            "Referrer: http://mobileapp.stayzilla.com/",
    })
    @POST("/places")
    Call<PlacesModel> getEvents(@Body EventsRequest eventsRequest);

}
