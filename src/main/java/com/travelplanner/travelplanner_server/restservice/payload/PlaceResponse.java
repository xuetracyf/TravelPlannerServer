package com.travelplanner.travelplanner_server.restservice.payload;

import com.travelplanner.travelplanner_server.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PlaceResponse {
    private String place_id;
    private String name;
    private List<String> photo_reference;
    private String description;
    private int likes;
    private Date createTime;
    private List<Comment> comments;
}
