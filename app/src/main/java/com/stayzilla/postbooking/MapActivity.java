package com.stayzilla.postbooking;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stayzilla.postbooking.model.Event;
import com.stayzilla.postbooking.model.PlacesModel;
import com.stayzilla.postbooking.network.restclients.EventsAPIRestClient;
import com.stayzilla.postbooking.util.OverflowLayoutDrawer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Event> events;
    private boolean isEventsAdded;
    private TextView editEventsTextView, closeTextView;
    private LinearLayout eventsLinearLayout;
    private LinearLayout eventsMainLinearLayout;
    private OverflowLayoutDrawer overflowLayoutDrawer;
    private ArrayList<String> selectedEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        overflowLayoutDrawer = new OverflowLayoutDrawer(this);
        closeTextView = (TextView) findViewById(R.id.closeButton);
        editEventsTextView = (TextView) findViewById(R.id.editEventsTextview);
        eventsMainLinearLayout = (LinearLayout) findViewById(R.id.eventsMainLayout);
        eventsLinearLayout = (LinearLayout) findViewById(R.id.eventsLayout);

        editEventsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventsMainLinearLayout.getVisibility() == View.VISIBLE) {
                    eventsMainLinearLayout.setVisibility(View.GONE);
                } else {
                    eventsMainLinearLayout.setVisibility(View.VISIBLE);
                }
//                editEventsTextView.setVisibility(View.GONE);
            }
        });

        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsMainLinearLayout.setVisibility(View.GONE);
//                editEventsTextView.setVisibility(View.VISIBLE);
            }
        });

        sendEventRequest();
        addEventsInOverFlowLayout();

    }

    private void addEventsInOverFlowLayout() {

        String events[] = new String[]{"ATM", "Airport", "Bar", "Hospital", "Night Club"};
        String eventsName[] = new String[]{"atm", "airport", "bar", "hospital", "night_club"};

        LinearLayout eventsView[] = new LinearLayout[events.length];
        for (int i = 0; i < events.length; i++) {
            TextView textView = new TextView(this);
            textView.setPadding(30, 10, 30, 10);
            textView.setText(events[i]);
            textView.setTag(eventsName[i]);
            textView.setBackgroundResource(R.drawable.blacklist_language_checkbox_checked);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String tag = (String) view.getTag();
                    if (selectedEvents.contains(tag)) {
                        view.setBackgroundResource(R.drawable.blacklist_language_checkbox_unchecked);
                        selectedEvents.remove(tag);
                    } else {
                        view.setBackgroundResource(R.drawable.blacklist_language_checkbox_checked);
                        selectedEvents.add(tag);
                    }

                }
            });

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setPadding(10, 10, 10, 10);
            linearLayout.addView(textView);
            ;

            eventsView[i] = linearLayout;
        }
        overflowLayoutDrawer.populateText(eventsLinearLayout, eventsView);

    }

    private void sendEventRequest() {

        EventsAPIRestClient.getClient().getEvents("24.8366999", "79.9213073").enqueue(new Callback<PlacesModel>() {
            @Override
            public void onResponse(Call<PlacesModel> call, Response<PlacesModel> response) {

                if (response.isSuccessful() && mMap != null) {

                    PlacesModel body = response.body();
                    events = body.events;
                    addEventsInMap();

                }

            }

            @Override
            public void onFailure(Call<PlacesModel> call, Throwable t) {

            }
        });

    }

    private void addEventsInMap() {
        for (Event event : events) {
            LatLng latLng = new LatLng(event.lat, event.lon);
            mMap.addMarker(new MarkerOptions().position(latLng).title(event.name));
        }
        isEventsAdded = true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if ((!isEventsAdded) && events != null) {
            addEventsInMap();
        }

        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
