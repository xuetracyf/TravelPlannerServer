package com.travelplanner.travelplanner_server.restservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class PlanResponse {
    private String token;
    private String user_id;
    private List<String> place_id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}

