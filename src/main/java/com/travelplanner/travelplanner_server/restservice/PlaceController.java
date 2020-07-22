package com.travelplanner.travelplanner_server.restservice;

import com.travelplanner.travelplanner_server.restservice.payload.PlaceDetailResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.travelplanner.travelplanner_server.exception.InvalidPlaceIdException;
import com.travelplanner.travelplanner_server.model.Place;
import com.travelplanner.travelplanner_server.mongodb.dal.PlaceDAL;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor

public class PlaceController {

    @Autowired
    private final PlaceDAL placeDAL;

    /**
     * Get a single place detail with place_id.
     * locahost：8080/attraction/12345？max_width=200
     * @param placeId place_id
     * @param maxWidth max width of the attraction pic, default 400
     * @return
     */
    @RequestMapping(value = "/attraction/{placeid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Place> getOnePlace(@PathVariable("placeid") String placeId, @RequestParam("max_width") String maxWidth) {
        if (placeId == null) {
            throw new InvalidPlaceIdException();
        }
        int width = maxWidth == null ? 400 : Integer.parseInt(maxWidth);
        return ResponseEntity.ok(placeDAL.getSinglePlace(placeId, width));
    }

    /**
     * Get all place details.
     * @param maxWidth max width of the attraction pic, default 400
     * @return
     */
    @RequestMapping(value = "/attraction", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Place>> getAllPlace(@RequestParam("max_width") String maxWidth) {
        int width = maxWidth == null ? 400 : Integer.parseInt(maxWidth);
        return ResponseEntity.ok(placeDAL.getAllPlace(width));

    }
        @GetMapping(value = "/attraction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<PlaceDetailResponse> getAttractionDetail(@RequestHeader("Authentication") String tokenHeader,
                                                                       @PathVariable("id") String id) {
            String jwtToken = tokenHeader.substring(7);
            return ResponseEntity.ok().body(new PlaceDetailResponse());
        }
}
