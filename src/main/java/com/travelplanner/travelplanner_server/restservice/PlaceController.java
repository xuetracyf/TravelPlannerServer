package com.travelplanner.travelplanner_server.restservice;


import com.travelplanner.travelplanner_server.restservice.payload.PlaceDetailResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {
    @GetMapping(value = "/attraction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaceDetailResponse> getAttractionDetail(@RequestHeader("Authentication") String tokenHeader,
                                                                   @PathVariable("id") String id) {
        String jwtToken = tokenHeader.substring(7);
        return ResponseEntity.ok().body(new PlaceDetailResponse());

    }
}
