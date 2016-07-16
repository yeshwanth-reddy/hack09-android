package com.stayzilla.postbooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stayzilla.postbooking.model.PlacesModel;
import com.stayzilla.postbooking.network.restclients.EventsAPIRestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, MapActivity.class));


        /*EventsAPIRestClient.getClient().getEvents("12","121").enqueue(new Callback<PlacesModel>() {
            @Override
            public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {

            }

            @Override
            public void onFailure(Call<PlacesModel> call, Throwable t) {

            }
        });*/

    }
}
