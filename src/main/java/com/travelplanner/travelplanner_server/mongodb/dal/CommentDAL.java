package com.travelplanner.travelplanner_server.mongodb.dal;

import com.travelplanner.travelplanner_server.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAL {

    @Autowired
    private final MongoTemplate mongoTemplate;

    public CommentDAL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Create a Comment instance and store it to mongoDB
     * @param comment
     * @return a new instance of comment
     */
    public Comment createComment(Comment comment) {
        return mongoTemplate.insert(comment);
    }

//    protected boolean removeComment(String content) {
//
//        Query query = new Query();
//        query.addCriteria(Criteria.where("content").is(content));
//        return mongoTemplate.doFindAndDelete();
//    }

}
