package com.travelplanner.travelplanner_server.mongodb.dal;

import com.travelplanner.travelplanner_server.model.Like;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


@Repository
@AllArgsConstructor
public class LikeDAL {

    @Autowired
    private final MongoTemplate mongoTemplate;

    /**
     * add a like object to db
     * @param like
     */
    public void addLike(Like like) {
        mongoTemplate.insert(like);
    }



}
