package com.travelplanner.travelplanner_server.restservice;

import com.travelplanner.travelplanner_server.restservice.payload.PlaceIdList;
import com.travelplanner.travelplanner_server.restservice.payload.RecommendRequest;
import com.travelplanner.travelplanner_server.restservice.payload.RecommendResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

public class RecommendController {

    @PostMapping(value = "/recommend", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecommendResponse> recommend(@RequestBody RecommendRequest req) {
        if (req == null || req.getPlace_ids() == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        return ResponseEntity.ok().body(new RecommendResponse("OK", new PlaceIdList(new ArrayList<>())));
    }
}
