package com.stayzilla.postbooking.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nitesh on 7/16/16.
 */
public class MyBookingsModel {

    @SerializedName("data")
    public List<MyBooking> myBooking;

}
