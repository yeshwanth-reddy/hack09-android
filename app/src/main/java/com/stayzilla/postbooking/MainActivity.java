package com.stayzilla.postbooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.stayzilla.postbooking.model.MyBooking;
import com.stayzilla.postbooking.model.MyBookingsModel;
import com.stayzilla.postbooking.network.restclients.MyBookingsAPIRestClient;
import com.stayzilla.postbooking.util.GCMUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdetail);

        GCMUtil.registerWithGCMServerInBackground(this);

        ImageView imageView = (ImageView) findViewById(R.id.hotelImage);

        Picasso.with(this)
                .load("https://stay-imgs.stayzilla.com/resize/1200x800/89946/1124146.jpg")
                .placeholder(R.drawable.ic_hotel_placeholder)
                .error(R.drawable.ic_hotel_placeholder)
                .into(imageView);

        imageView = (ImageView) findViewById(R.id.locationImage);

        Picasso.with(this)
                .load("https://maps.googleapis.com/maps/api/staticmap?center=24.8366999,79.9213073&zoom=17&markers=color:pink%7Clabel:%7C24.8366999,79.9213073&size=800x400")
                .placeholder(R.drawable.ic_hotel_placeholder)
                .error(R.drawable.ic_hotel_placeholder)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });

        Switch aSwitch1 = (Switch) findViewById(R.id.switch1);
        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(MainActivity.this, b ? "FB Check In reminder enabled" : "FB Check In reminder disabled", Toast.LENGTH_SHORT).show();
            }
        });

        Switch aSwitch = (Switch) findViewById(R.id.switch2);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                String items[] = new String[]{"Shoes for Trekking", "Jerkin"};
                builder.setTitle("Few Things to Remember")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                            }
                        });
                builder.show();
            }
        });


        TextView textView = (TextView) findViewById(R.id.share);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Art Home Stay\nOrder ID : 123455649893830\nCheck In : 21 Aug 2016\nCheck Out : 24 Aug 2016\n-Stayzilla");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

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

                if (response.isSuccessful()) {
                    getBookings(response.body());
                } else {
                    getBookings(new MyBookingsModel());
                }


            }

            @Override
            public void onFailure(Call<MyBookingsModel> call, Throwable t) {

            }
        });

    }

    public void getBookings(MyBookingsModel bookingsResponse) {
        List<MyBooking> myBookings = bookingsResponse.myBooking;
        for (int i = 0; i < myBookings.size(); i++) {
            MyBooking myBooking = myBookings.get(i);
            String response = new Gson().toJson(myBooking);
            System.out.println("Logging My Booking=" + response);
            System.out.println(myBooking.stay.location.lat);
        }
    }
}
