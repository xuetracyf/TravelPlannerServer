package com.travelplanner.travelplanner_server.googleMapApi;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class DistanceAPI {
    // Vancouver Aquarium: ChIJp2zKeo1xhlQRWOMOmCcWJV8
    // Canada Place: ChIJIeDiJJ1xhlQRCWHIheB_Bbc
    // Museum of Vancouver: ChIJHZSzNzNyhlQRzmus61cqE5Y
    private static final GeoApiContext context = Context.getContext();

    public static MutableValueGraph<String, Double> generateDistGraph(LatLng startPoint, List<String> place_ids) {
        MutableValueGraph<String, Double> distGraph = ValueGraphBuilder.undirected().build();
        List<String> points = new ArrayList<>();
        points.add(startPoint.lat + ", " + startPoint.lng);
        // Add Node
        for (String place_id : place_ids) {
            distGraph.addNode(place_id);
            points.add(place_id);
        }
        String[] placeArray = (String[]) points.toArray();
        DistanceMatrixApiRequest req = DistanceMatrixApi.getDistanceMatrix(context, placeArray, placeArray);

        return distGraph;
    }
}
