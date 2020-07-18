package com.travelplanner.travelplanner_server.restservice;

import com.google.common.graph.ValueGraph;
import com.google.maps.errors.ApiException;
import com.travelplanner.travelplanner_server.algorithm.TSP;
import com.travelplanner.travelplanner_server.exception.GoogleMapAPIException;
import com.travelplanner.travelplanner_server.googleMapApi.DistanceAPI;
import com.travelplanner.travelplanner_server.restservice.payload.RecommendData;
import com.travelplanner.travelplanner_server.restservice.payload.RecommendRequest;
import com.travelplanner.travelplanner_server.restservice.payload.RecommendResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RecommendController {

    @PostMapping(value = "/recommend", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecommendResponse> recommend(@RequestBody RecommendRequest req) {
        if (req == null || req.getPlace_ids() == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        ValueGraph distGraph;
        try {
            distGraph = DistanceAPI.generateDistGraph(req.getStartPosition(), req.getPlace_ids());
        } catch (ApiException e) {
            e.printStackTrace();
            throw new GoogleMapAPIException();
        }
        TSP.RouteCost routeCost = TSP.findShortestPath(distGraph);
        return ResponseEntity.ok().body(new RecommendResponse("OK",
                new RecommendData(routeCost.distance, routeCost.visitOrder)));
    }
}
