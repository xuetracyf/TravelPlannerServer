package com.travelplanner.travelplanner_server.mongodb.dal;

import com.google.maps.model.LatLng;
import com.travelplanner.travelplanner_server.model.Place;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

@Repository
@AllArgsConstructor
public class PlaceDAL {

    @Autowired
    private final MongoTemplate mongoTemplate;
    private final CommentDAL commentDAL;


    /**
     * retrieve a single place details object by place id
     * @param placeId place id you want to rtrieve
     * @param maxWidth place pic size, default 400
     * @return the corresponding place object
     */
    public Place getSinglePlace(String placeId, int maxWidth) {
        if (hasPlace(placeId)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(placeId));
            return mongoTemplate.findOne(query, Place.class);
        }
        return createPlace(placeId);
    }

    /**
     * FAKE TEST FUNCTION
     * @return a place object that has been stored in db
     */
    private Place createPlace(String placeId) {
        Place place = Place.builder()
                .id(placeId)
                .name("SF")
                .location(new LatLng())
                .photo_refs(null)
                .description("description")
                .upVotes(10)
                .createTime(new Date())
                .build();
        return mongoTemplate.insert(place);
    }

    /**
     * @param placeId
     * @return
     */
    public boolean hasPlace(String placeId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(placeId));
        return mongoTemplate.exists(query, Place.class);
    }

    /**
     * retrieve all place details object
     * @param maxWidth pic max width, default 400
     * @return
     */
    public List<Place> getAllPlace(int maxWidth) {
        return mongoTemplate.findAll(Place.class, "place");
    }

//    public boolean updateUpVotes(String placeId){
//        Update update = new
//    }
}
