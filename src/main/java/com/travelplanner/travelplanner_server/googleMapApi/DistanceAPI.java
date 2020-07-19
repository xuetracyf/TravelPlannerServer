package com.travelplanner.travelplanner_server.googleMapApi;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistanceAPI {
    // Vancouver Aquarium: ChIJp2zKeo1xhlQRWOMOmCcWJV8
    // Canada Place: ChIJIeDiJJ1xhlQRCWHIheB_Bbc
    // Museum of Vancouver: ChIJHZSzNzNyhlQRzmus61cqE5Y

    // Google Graph: https://github.com/google/guava/wiki/GraphsExplained
    private static final GeoApiContext context = Context.getContext();

    public static ValueGraph<String, Long> generateDistGraph(LatLng startPoint, List<String> place_ids)
            throws ApiException {
        MutableValueGraph<String, Long> distGraph = ValueGraphBuilder.directed().build();
        distGraph.addNode("startPosition");
        List<String> points = new ArrayList<>();
        // Add Start point and placeID as a node.
        points.add(startPoint.lat + ", " + startPoint.lng);
        for (String place_id : place_ids) {
            distGraph.addNode(place_id);
            points.add("place_id:" + place_id);
        }
        // Convert list<String> to String[]
        String[] placeArray = points.toArray(new String[0]);
        DistanceMatrixApiRequest req = DistanceMatrixApi.getDistanceMatrix(context, placeArray, placeArray);
        DistanceMatrix distMatrix;

        try {
            distMatrix = req.await();
        } catch (InterruptedException | IOException e) {
            // Call API again if internal error happens
            e.printStackTrace();
            return generateDistGraph(startPoint, place_ids);
        }

        // Add distance edge
        for (int i = 0; i < distMatrix.rows.length; i++) {
            DistanceMatrixRow row= distMatrix.rows[i];
            for (int j = 0; j < row.elements.length; j++) {
                if (i == j) {
                    continue;
                }
                DistanceMatrixElement element = row.elements[j];
                String origin = i == 0 ? "startPosition" : place_ids.get(i-1);
                String destination = j == 0 ? "startPosition" : place_ids.get(j-1);
                distGraph.putEdgeValue(origin, destination, element.distance.inMeters);
            }
        }
        return distGraph;
    }

    public static void main(String[] args) throws InterruptedException, ApiException, IOException {
        List<String> points = new ArrayList<>();
        points.add("49.255801, -123.184393");
        points.add("place_id:ChIJp2zKeo1xhlQRWOMOmCcWJV8");
        points.add("place_id:ChIJIeDiJJ1xhlQRCWHIheB_Bbc");
        points.add("place_id:ChIJHZSzNzNyhlQRzmus61cqE5Y");
        String[] placeArray = points.toArray(new String[0]);
        DistanceMatrixApiRequest req = DistanceMatrixApi.getDistanceMatrix(context, placeArray, placeArray);

        DistanceMatrix distMatrix = req.await();
        for (DistanceMatrixRow row: distMatrix.rows) {
            for (DistanceMatrixElement element: row.elements) {
                System.out.println(element.distance.humanReadable);
            }
            System.out.println("next row");
        }
    }
}
