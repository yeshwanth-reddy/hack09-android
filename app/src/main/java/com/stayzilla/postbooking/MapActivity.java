package com.stayzilla.postbooking;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stayzilla.postbooking.model.Event;
import com.stayzilla.postbooking.model.EventsRequest;
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
        System.out.println("onCreat for Map Activity log");
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

        addEventsInOverFlowLayout();
        sendEventRequest();

    }

    private void addEventsInOverFlowLayout() {

        selectedEvents.add("airport");
        selectedEvents.add("atm");
        selectedEvents.add("restaurant");
        selectedEvents.add("pharmacy");
        selectedEvents.add("hospital");

        String events[] = new String[]{"ATM", "Airport", "Bar", "Hospital", "Pharmacy", "Night Club", "Restaurant","Shopping Mall","Train Station"};
        String eventsName[] = new String[]{"atm", "airport", "bar", "hospital", "pharmacy", "night_club", "restaurant","shopping_mall","train_station"};

        LinearLayout eventsView[] = new LinearLayout[events.length];
        for (int i = 0; i < events.length; i++) {
            TextView textView = new TextView(this);
            textView.setPadding(30, 10, 30, 10);
            textView.setText(events[i]);
            textView.setTag(eventsName[i]);
            if (selectedEvents.contains(eventsName[i])) {
                textView.setBackgroundResource(R.drawable.blacklist_language_checkbox_checked);
            } else {
                textView.setBackgroundResource(R.drawable.blacklist_language_checkbox_unchecked);
            }

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

        EventsRequest eventsRequest = new EventsRequest();
        eventsRequest.lat = "24.8366999";
        eventsRequest.lon = "79.9213073";
        List<String> types = new ArrayList<>();
        types.addAll(selectedEvents);
        eventsRequest.types = types;

        EventsAPIRestClient.getClient().getEvents(eventsRequest).enqueue(new Callback<PlacesModel>() {
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
                t.printStackTrace();
            }
        });

    }

    private void addEventsInMap() {

        BitmapDescriptor bitmapDescriptor[] = new BitmapDescriptor[10];
        bitmapDescriptor[0] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        bitmapDescriptor[1] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
        bitmapDescriptor[2] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
        bitmapDescriptor[3] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        bitmapDescriptor[4] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
        bitmapDescriptor[5] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
        bitmapDescriptor[6] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        bitmapDescriptor[7] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE);
        bitmapDescriptor[8] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
        bitmapDescriptor[9] = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);

        String type = "";
        int count = 0;

        LatLng latLng = null;
        for (Event event : events) {
            latLng = new LatLng(event.lat, event.lon);
            mMap.addMarker(new MarkerOptions().position(latLng).title(event.name).icon(bitmapDescriptor[count]));
            if(!event.type.equalsIgnoreCase(type)) {
                type = event.type;
                count++;
            }
        }
        if(latLng != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 12.0f));
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
