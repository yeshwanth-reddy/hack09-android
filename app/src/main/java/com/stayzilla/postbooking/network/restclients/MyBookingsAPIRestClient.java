package com.stayzilla.postbooking.network.restclients;

import com.stayzilla.postbooking.model.MyBookingsModel;
import com.stayzilla.postbooking.network.RestServiceGenerator;
import com.stayzilla.postbooking.network.URLConstants;
import com.stayzilla.postbooking.network.contract.EventContract;
import com.stayzilla.postbooking.network.contract.MyBookingsContract;

/**
 * Created by nitesh on 7/16/16.
 */


public class MyBookingsAPIRestClient {


    private static final MyBookingsAPIRestClient restClient = new MyBookingsAPIRestClient();
    private static MyBookingsContract myBookingsContract = null;

    private MyBookingsAPIRestClient(){
    };

    public static MyBookingsContract getClient(){
        initialize();
        return myBookingsContract;
    }

    private static void initialize(){
        if (restClient == null || myBookingsContract == null){
            myBookingsContract = RestServiceGenerator.createRestApiService(MyBookingsContract.class, URLConstants.SEARCH_BASE_URL);
        }
    }
}
