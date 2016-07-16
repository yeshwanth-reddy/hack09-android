package com.stayzilla.postbooking.network.restclients;

import com.stayzilla.postbooking.network.RestServiceGenerator;
import com.stayzilla.postbooking.network.URLConstants;
import com.stayzilla.postbooking.network.contract.EventContract;

public class EventsAPIRestClient {

    private static final EventsAPIRestClient restClient = new EventsAPIRestClient();
    private static EventContract eventContract = null;

    private EventsAPIRestClient() {
    }

    public static EventContract getClient() {
        initialize();
        return eventContract;
    }

    private static void initialize() {
        if (restClient == null || eventContract == null) {
            eventContract = RestServiceGenerator.createRestApiService(
                    EventContract.class,
                    URLConstants.BASE_URL);
        }
    }

}