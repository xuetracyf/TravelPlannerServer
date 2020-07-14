package com.travelplanner.travelplanner_server.googleMapApi;

import com.google.maps.GeoApiContext;


// Google Map API documentation: https://www.javadoc.io/doc/com.google.maps/google-maps-services/latest/index.html

public class Context {
    // Remove your API key before push your branch!!! otherwise everyone knows your key...
    private static final String API_KEY = ""; // "Please input your key here"
    private static final GeoApiContext context = new GeoApiContext.Builder().apiKey(API_KEY).build();

    public static GeoApiContext getContext() {
        return context;
    }

}
