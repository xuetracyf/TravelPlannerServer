package com.travelplanner.travelplanner_server.restservice.payload;

<<<<<<< HEAD
import com.google.maps.model.LatLng;
=======
>>>>>>> 8b1e21c... place api without map
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
<<<<<<< HEAD
    private LatLng location;
    private List<String> photo_reference;
=======
    private String photo_reference;
>>>>>>> 8b1e21c... place api without map
    private String description;
    private int likes;
    private Date createTime;
    private List<Comment> comments;
}
