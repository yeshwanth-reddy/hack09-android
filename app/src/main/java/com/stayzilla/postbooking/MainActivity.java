package com.stayzilla.postbooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.stayzilla.postbooking.model.MyBooking;
import com.stayzilla.postbooking.model.MyBookingsModel;
import com.stayzilla.postbooking.model.PlacesModel;
import com.stayzilla.postbooking.network.restclients.EventsAPIRestClient;
import com.stayzilla.postbooking.network.restclients.MyBookingsAPIRestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startActivity(new Intent(this, MapActivity.class));


//        EventsAPIRestClient.getClient().getEvents("12","121").enqueue(new Callback<PlacesModel>() {
//            @Override
//            public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<PlacesModel> call, Throwable t) {
//
//            }
//        });

        MyBookingsAPIRestClient.getClient().getMyBookings("14676320834984kvIOHpsKBfY9PmVeLIMd5A9HYQ").enqueue(new Callback<MyBookingsModel>() {
            @Override
            public void onResponse(Call<MyBookingsModel> call, Response<MyBookingsModel> response) {

                if(response.isSuccessful()) {
                    getBookings(response.body());
                }
                else{
                    getBookings(new MyBookingsModel());
                }


            }

            @Override
            public void onFailure(Call<MyBookingsModel> call, Throwable t) {

            }
        });

    }

    public void getBookings(MyBookingsModel bookingsResponse){
        List<MyBooking> myBookings = bookingsResponse.myBooking;
        for (int i=0; i<myBookings.size(); i++ ) {
            MyBooking myBooking = myBookings.get(i);
            String response = new Gson().toJson(myBooking);
            System.out.println("Logging My Booking="+response);
            System.out.println(myBooking.stay.location.lat);
        }
    }
}
