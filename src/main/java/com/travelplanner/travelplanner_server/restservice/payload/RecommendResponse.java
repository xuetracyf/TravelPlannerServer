package com.travelplanner.travelplanner_server.restservice.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendResponse {

    private String status;
    private PlaceIdList data;

}
