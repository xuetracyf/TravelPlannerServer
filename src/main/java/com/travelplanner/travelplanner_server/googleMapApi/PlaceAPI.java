package com.travelplanner.travelplanner_server.googleMapApi;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.travelplanner.travelplanner_server.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceAPI {

    private static final GeoApiContext context = Context.getContext();

    // Simple method for demo
    public List<Place> searchAllNearbyPlaces(double cityLat, double cityLon) {
        NearbySearchRequest req = PlacesApi.nearbySearchQuery(context, new LatLng(cityLat, cityLon));
        PlacesSearchResponse res;
        // Synchronous
        try {
            res = req.await();
            // Handle successful request.
        } catch (Exception e) {
            // Handle error
        }
        // Convert res to any data model you want. e.g. List<Place>
        return new ArrayList<Place>();
    }


}
