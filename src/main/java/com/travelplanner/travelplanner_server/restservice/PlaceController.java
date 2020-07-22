package com.travelplanner.travelplanner_server.restservice;

import com.travelplanner.travelplanner_server.restservice.payload.PlaceDetailResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.travelplanner.travelplanner_server.exception.InvalidPlaceIdException;
import com.travelplanner.travelplanner_server.model.Comment;
import com.travelplanner.travelplanner_server.model.Place;
import com.travelplanner.travelplanner_server.mongodb.dal.CommentDAL;
import com.travelplanner.travelplanner_server.mongodb.dal.PlaceDAL;
import com.travelplanner.travelplanner_server.restservice.payload.PlaceResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor

public class PlaceController {

    @Autowired
    private final PlaceDAL placeDAL;
    private final CommentDAL commentDAL;

    /**
     * Get a single place detail with place_id.
     * locahost：8080/attraction/12345？max_width=200
     * @param placeId place_id
     * @param maxWidth max width of the attraction pic, default 400
     * @return
     */
    @RequestMapping(value = "/attraction/{placeid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<PlaceResponse> getOnePlace(@PathVariable("placeid") String placeId,
                                             @RequestParam(value = "max_width", required = false) String maxWidth) {
        if (placeId == null) {
            throw new InvalidPlaceIdException();
        }

        int width = maxWidth == null ? 400 : Integer.parseInt(maxWidth);
        Place place = placeDAL.getSinglePlace(placeId, width);
        List<Comment> comments = commentDAL.getAllCommentById(placeId);
        PlaceResponse placeResponse = PlaceResponse.builder()
                .place_id(placeId)
                .name(place.getName())
                .location(place.getLocation())
                .description(place.getDescription())
                .photo_reference(place.getPhoto_refs())
                .likes(place.getLikes())
                .comments(comments)
                .createTime(place.getCreateTime())
                .build();
        return ResponseEntity.ok(placeResponse);
    }

    /**
     * Get all place details.
     * @param maxWidth max width of the attraction pic, default 400
     * @return
     */

    @RequestMapping(value = "/attractions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Place>> getAllPlace(@RequestParam(value = "max_width", required = false) String maxWidth) {
        int width = maxWidth == null ? 400 : Integer.parseInt(maxWidth);
        return ResponseEntity.ok(placeDAL.getAllPlace(width));

    }
}
